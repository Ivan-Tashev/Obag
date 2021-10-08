package bg.obag.obag.service;

import bg.obag.obag.model.binding.ProductAddBindingModel;
import bg.obag.obag.model.entity.Product;
import bg.obag.obag.model.service.ProductServiceModel;

import java.util.List;

public interface ProductsService {
    ProductServiceModel addProduct(ProductAddBindingModel productServiceModel);

    List<ProductServiceModel> findAllOrderByCategory();

    List<ProductServiceModel> findByCategory(String category);

    ProductServiceModel findProductById(Long id);
}
