package bg.obag.obag.service;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.service.CartServiceModel;
import org.springframework.security.core.userdetails.UserDetails;

public interface CartService {

    CartServiceModel createCart(Long id, UserDetails principal) throws ProductNotFoundException;

    CartServiceModel findById(Long id);

    CartServiceModel addProductToCart(Long cartId, Long productId) throws ProductNotFoundException;

    CartServiceModel deleteProductFromCart(Long cartId, Long productId) throws ProductNotFoundException;
}
