package bg.obag.obag.service.impl;

import bg.obag.obag.model.binding.ProductAddBindingModel;
import bg.obag.obag.model.entity.Product;
import bg.obag.obag.model.entity.enums.Category;
import bg.obag.obag.model.entity.enums.Season;
import bg.obag.obag.model.service.ProductServiceModel;
import bg.obag.obag.repo.ProductRepo;
import bg.obag.obag.security.CurrentUser;
import bg.obag.obag.service.ProductsService;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    private final ProductRepo productRepo;
    private final UserService userService;
    private final CurrentUser currentUser;
    private final ModelMapper modelMapper;

    public ProductsServiceImpl(ProductRepo productRepo, UserService userService, CurrentUser currentUser, ModelMapper modelMapper) {
        this.productRepo = productRepo;
        this.userService = userService;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProduct(ProductAddBindingModel productAddBindingModel) {
        ProductServiceModel productServiceModel =
                modelMapper.map(productAddBindingModel, ProductServiceModel.class)
                        .setCategory(Category.valueOf(productAddBindingModel.getCategory().toUpperCase()))
                        .setSeason(Season.valueOf(productAddBindingModel.getSeason()))
                        .setCost(BigDecimal.valueOf(productAddBindingModel.getCost()))
                        .setPrice(BigDecimal.valueOf(productAddBindingModel.getPrice()))
                        .setCreatedOn(LocalDateTime.now())
                        .setCreatedBy(userService.findById(currentUser.getId()).get());

        productRepo.save(modelMapper.map(productServiceModel, Product.class));

        return productServiceModel;
    }

    @Override
    public List<ProductServiceModel> findAllOrderByCategory() {
        return productRepo.findAllByCategory().stream()
                .map(product -> modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductServiceModel> findByCategory(String category) {
        return productRepo.findByCategory(Category.valueOf(category.toUpperCase())).stream()
                .map(product -> modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(Long id) {
        return modelMapper.map(productRepo.findById(id), ProductServiceModel.class);
    }
}
