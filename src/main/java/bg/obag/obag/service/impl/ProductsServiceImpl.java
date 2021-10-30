package bg.obag.obag.service.impl;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.binding.ProductAddBindingModel;
import bg.obag.obag.model.binding.ProductUpdateBindingModel;
import bg.obag.obag.model.entity.ProductEntity;
import bg.obag.obag.model.entity.enums.Category;
import bg.obag.obag.model.entity.enums.Season;
import bg.obag.obag.model.service.ProductServiceModel;
import bg.obag.obag.repo.ProductRepo;
import bg.obag.obag.service.ProductsService;
import bg.obag.obag.service.UserService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.json";
    private final ProductRepo productRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public ProductsServiceImpl(ProductRepo productRepo, UserService userService, ModelMapper modelMapper, Gson gson) {
        this.productRepo = productRepo;
        this.userService = userService;
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
                    ProductEntity productEntity = modelMapper.map(productServiceModel, ProductEntity.class);
                    productEntity.setCreatedOn(LocalDateTime.now());
                    // TODO:
//                    productEntity.setCreatedBy(userService.findById(currentUser.getId()).get());
                    return productEntity;
                })
                .forEach(productRepo::save);
    }

    @Override
    public ProductServiceModel addProduct(ProductAddBindingModel productAddBindingModel, Principal principal) throws ProductNotFoundException {
        ProductServiceModel productServiceModel =
                modelMapper.map(productAddBindingModel, ProductServiceModel.class)
                        .setCategory(Category.valueOf(productAddBindingModel.getCategory()))
                        .setSeason(Season.valueOf(productAddBindingModel.getSeason()))
                        .setCreatedOn(LocalDateTime.now())
                        .setCreatedBy(principal.getName());

        productRepo.save(modelMapper.map(productServiceModel, ProductEntity.class)
                .setCreatedBy(userService.findByEmail(productServiceModel.getCreatedBy()).get()));

        return productServiceModel;
    }

    @Override
    public ProductServiceModel updateProduct(ProductUpdateBindingModel productUpdateBindingModel, Principal principal) throws ProductNotFoundException {
        ProductServiceModel productServiceModel =
                modelMapper.map(productUpdateBindingModel, ProductServiceModel.class)
                        .setCategory(Category.valueOf(productUpdateBindingModel.getCategory()))
                        .setSeason(Season.valueOf(productUpdateBindingModel.getSeason()))
                        .setCreatedBy(principal.getName());

        ProductEntity productEntity = productRepo.findById(productServiceModel.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productServiceModel.getId() + " not found."));

        productEntity.setName(productServiceModel.getName())
                .setSku(productServiceModel.getSku())
                .setCategory(productServiceModel.getCategory())
                .setSeason(productServiceModel.getSeason())
                .setMetric(productServiceModel.getMetric())
                .setCost(productServiceModel.getCost())
                .setPrice(productServiceModel.getPrice())
                .setBarcode(productServiceModel.getBarcode())
                .setDescription(productServiceModel.getDescription())
                .setCreatedBy(userService.findByEmail(productServiceModel.getCreatedBy()).get())
                .setImage(productServiceModel.getImage())
                .setDeleted(productServiceModel.getDeleted());
        productRepo.save(productEntity);

        return productServiceModel;
    }

    @Override
    public List<ProductServiceModel> findAllOrderByCategory() {
        return productRepo.findAllByCategory().stream()
                .map(product -> modelMapper.map(product, ProductServiceModel.class)
                        .setCreatedBy(product.getCreatedBy().getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductServiceModel> findByCategory(String category) {
        return productRepo.findByCategoryAndDeletedIsFalse(Category.valueOf(category.toUpperCase())).stream()
                .map(product -> modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(Long id) throws ProductNotFoundException {
        ProductEntity productEntity = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found."));
        return modelMapper.map(productEntity, ProductServiceModel.class)
                .setCreatedBy(productEntity.getCreatedBy().getEmail());
    }

    @Override
    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public boolean checkNameExists(String name) {
        return productRepo.existsByName(name);
    }

    @Override
    public boolean checkSkuExists(String sku) {
        return productRepo.existsBySku(sku);
    }

    @Override
    public boolean checkBarcodeExists(Long barcode) {
        return productRepo.existsByBarcode(barcode);
    }

    @Override
    public boolean checkNameExistsExceptId(String name, Long id) {
        return productRepo.findByNameExceptId(name, id).isPresent();
    }

    @Override
    public boolean checkSkuExistsExceptId(String sku, Long id) {
        return productRepo.findBySkuExceptId(sku, id).isPresent();
    }

    @Override
    public boolean checkBarcodeExistsExceptId(Long barcode, Long id) {
        return productRepo.findByBarcodeExceptId(barcode, id).isPresent();
    }
}
