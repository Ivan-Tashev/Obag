package bg.obag.obag.service.impl;

import bg.obag.obag.exception.CategoryNotFoundException;
import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.binding.ProductAddBindingModel;
import bg.obag.obag.model.binding.ProductUpdateBindingModel;
import bg.obag.obag.model.entity.CategoryEntity;
import bg.obag.obag.model.entity.ProductEntity;
import bg.obag.obag.model.entity.UserEntity;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.json";
    private final Logger LOGGER = LoggerFactory.getLogger(ProductsServiceImpl.class);
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserEntity currentUserEntity = userService.findByEmail(email).orElseThrow();

        Arrays.stream(gson.fromJson(readString, ProductServiceModel[].class))
                .filter(product -> {
                    boolean valid = true; // TODO: implement validation
                    return valid;
                })
                .map(productServiceModel -> {
                    ProductEntity productEntity = modelMapper.map(productServiceModel, ProductEntity.class);
                    productEntity
                            .setCategory(categoryRepo.findByCategory(productServiceModel.getCategory()).get())
                            .setSeason(seasonRepo.findBySeason(productServiceModel.getSeason()).get())
                            .setCreatedBy(currentUserEntity);
                    return productEntity;
                })
                .forEach(productRepo::save);
    }

    @Override
    public ProductServiceModel addProduct(ProductAddBindingModel productAddBindingModel, Principal principal) {
        purgeCache();
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
        purgeCache();
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

    @Cacheable("allProducts")
    @Override
    public List<ProductServiceModel> findAllOrderByCategory() {
        LOGGER.info("Products cache filled ('allProducts').");
        return productRepo.findAllByCategory().stream()
                .map(product -> modelMapper.map(product, ProductServiceModel.class)
                        .setCategory(product.getCategory().getCategory())
                        .setSeason(product.getSeason().getSeason())
                        .setCreatedBy(product.getCreatedBy().getEmail()))
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "allProducts", allEntries = true)
    public void purgeCache() {
        LOGGER.info("Purge cache('allProducts').");
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
    public ProductEntity findProductEntityById(Long id) throws ProductNotFoundException {
        return  productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found."));
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

    @Override
    public List<ProductServiceModel> findByCookie(String cart) {
        if (cart == null) return new ArrayList<>();

        String[] idsStrings = cart.split("-");
        List<Long> ids = new ArrayList<>();
        for (String id : idsStrings) {
            ids.add(Long.parseLong(id));
        }
        List<ProductEntity> productEntityList = productRepo.findByIds(ids);
        return productEntityList.stream()
                .map(productEntity -> modelMapper.map(productEntity, ProductServiceModel.class))
                .collect(Collectors.toList());
    }
}
