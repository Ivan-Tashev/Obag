package bg.obag.obag.service;

import bg.obag.obag.model.binding.ProductAddBindingModel;
import bg.obag.obag.model.service.ProductServiceModel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface ProductsService {

    void importProducts() throws IOException;

    ProductServiceModel addProduct(ProductAddBindingModel productServiceModel, Principal principal);

    List<ProductServiceModel> findAllOrderByCategory();

    List<ProductServiceModel> findByCategory(String category);

    ProductServiceModel findProductById(Long id);

    void deleteById(Long id);
}
