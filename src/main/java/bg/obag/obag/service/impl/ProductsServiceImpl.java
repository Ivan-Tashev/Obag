package bg.obag.obag.service.impl;

import bg.obag.obag.exception.CategoryNotFoundException;
import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.binding.ProductAddBindingModel;
import bg.obag.obag.model.binding.ProductUpdateBindingModel;
import bg.obag.obag.model.entity.CategoryEntity;
import bg.obag.obag.model.entity.ProductEntity;
import bg.obag.obag.model.service.ProductServiceModel;
import bg.obag.obag.repo.CategoryRepo;
import bg.obag.obag.repo.ProductRepo;
import bg.obag.obag.repo.SeasonRepo;
import bg.obag.obag.service.CategoryService;
import bg.obag.obag.service.ProductsService;
import bg.obag.obag.service.SeasonService;
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
    private final CategoryService categoryService;
    private final CategoryRepo categoryRepo;
    private final SeasonService seasonService;
    private final SeasonRepo seasonRepo;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public ProductsServiceImpl(ProductRepo productRepo, UserService userService, CategoryService categoryService, CategoryRepo categoryRepo, SeasonService seasonService, SeasonRepo seasonRepo, ModelMapper modelMapper, Gson gson) {
        this.productRepo = productRepo;
        this.userService = userService;
        this.categoryService = categoryService;
        this.categoryRepo = categoryRepo;
        this.seasonService = seasonService;
        this.seasonRepo = seasonRepo;
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
    public ProductServiceModel addProduct(ProductAddBindingModel productAddBindingModel, Principal principal) {

        ProductEntity productEntity = modelMapper.map(productAddBindingModel, ProductEntity.class)
                .setCategory(categoryRepo.findByCategory(productAddBindingModel.getCategory()).get())
                .setSeason(seasonRepo.findBySeason(productAddBindingModel.getSeason()).get())
                .setCreatedBy(userService.findByEmail(principal.getName()).get());

        ProductEntity savedProductEntity = productRepo.save(productEntity);

        return modelMapper.map(savedProductEntity, ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel updateProduct(ProductUpdateBindingModel productUpdateBindingModel, Principal principal)
            throws ProductNotFoundException {

        ProductEntity productEntity = productRepo.findById(productUpdateBindingModel.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + productUpdateBindingModel.getId() + " not found."));

        productEntity.setName(productUpdateBindingModel.getName())
                .setSku(productUpdateBindingModel.getSku())
                .setCategory(categoryRepo.findByCategory(productUpdateBindingModel.getCategory()).get())
                .setSeason(seasonRepo.findBySeason(productUpdateBindingModel.getSeason()).get())
                .setMetric(productUpdateBindingModel.getMetric())
                .setCost(productUpdateBindingModel.getCost())
                .setPrice(productUpdateBindingModel.getPrice())
                .setBarcode(productUpdateBindingModel.getBarcode())
                .setDescription(productUpdateBindingModel.getDescription())
                .setImage(productUpdateBindingModel.getImage())
                .setDeleted(productUpdateBindingModel.isDeleted())
                .setCreatedBy(userService.findByEmail(principal.getName()).get());

        ProductEntity updatedProductEntity = productRepo.save(productEntity);

        return modelMapper.map(updatedProductEntity, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllOrderByCategory() {
        return productRepo.findAllByCategory().stream()
                .map(product -> modelMapper.map(product, ProductServiceModel.class)
                        .setCategory(product.getCategory().getCategory())
                        .setSeason(product.getSeason().getSeason())
                        .setCreatedBy(product.getCreatedBy().getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductServiceModel> findByCategory(String category) throws CategoryNotFoundException {
         CategoryEntity categoryEntity = categoryRepo.findByCategory(category).
                 orElseThrow(() -> new CategoryNotFoundException("Category name " + category + " not found in database."));

        return productRepo.findByCategoryAndDeletedIsFalse(categoryEntity)
                .stream()
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
