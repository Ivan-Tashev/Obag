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
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.json";
    private final ProductRepo productRepo;
    private final UserService userService;
    private final CurrentUser currentUser;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public ProductsServiceImpl(ProductRepo productRepo, UserService userService, CurrentUser currentUser, ModelMapper modelMapper, Gson gson) {
        this.productRepo = productRepo;
        this.userService = userService;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void importProducts() throws IOException {
        String readString = Files.readString(Path.of(PRODUCTS_FILE_PATH));
        Arrays.stream(gson.fromJson(readString, ProductServiceModel[].class))
                .filter(product -> {
                    boolean valid = true; // TODO: implement validation
                    return valid;
                })
                .map(productServiceModel -> {
                    Product product = modelMapper.map(productServiceModel, Product.class);
                    product.setCreatedOn(LocalDateTime.now());
                    product.setCreatedBy(userService.findById(currentUser.getId()).get());
                    return product;
                })
                .forEach(productRepo::save);
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
        Product product = productRepo.findById(id).orElseThrow();
        return modelMapper.map(product, ProductServiceModel.class);
    }
}
