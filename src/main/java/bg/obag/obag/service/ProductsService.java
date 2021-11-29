package bg.obag.obag.service;

import bg.obag.obag.exception.CategoryNotFoundException;
import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.exception.SeasonNotFoundException;
import bg.obag.obag.model.binding.ProductAddBindingModel;
import bg.obag.obag.model.binding.ProductUpdateBindingModel;
import bg.obag.obag.model.entity.ProductEntity;
import bg.obag.obag.model.service.ProductServiceModel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ProductsService {

    void importProducts() throws IOException;

    ProductServiceModel addProduct(ProductAddBindingModel productServiceModel, Principal principal) throws ProductNotFoundException, CategoryNotFoundException, SeasonNotFoundException;

    ProductServiceModel updateProduct(ProductUpdateBindingModel productUpdateBindingModel, Principal principal) throws ProductNotFoundException, CategoryNotFoundException, SeasonNotFoundException;

    List<ProductServiceModel> findAllOrderByCategory();

    List<ProductServiceModel> findByCategory(String category) throws CategoryNotFoundException;

    ProductServiceModel findProductById(Long id) throws ProductNotFoundException;

    ProductEntity findProductEntityById(Long id) throws ProductNotFoundException;

    void deleteById(Long id);

    boolean checkNameExists(String name);

    boolean checkSkuExists(String sku);

    boolean checkBarcodeExists(Long barcode);

    boolean checkNameExistsExceptId(String name, Long id);

    boolean checkSkuExistsExceptId(String sku, Long id);

    boolean checkBarcodeExistsExceptId(Long barcode, Long id);

    List<ProductServiceModel> findByCookie(String cart);
}
