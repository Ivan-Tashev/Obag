package bg.obag.obag.service;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.service.CartServiceModel;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface CartService {

    CartServiceModel createCart(Long id, UserDetails principal) throws ProductNotFoundException;

    CartServiceModel findById(Long id);

    CartServiceModel addProductToCart(Long cartId, Long productId, UserDetails principal) throws ProductNotFoundException;

    CartServiceModel deleteProductFromCart(Long cartId, Long productId) throws ProductNotFoundException;

    void deleteSubmittedCart(Long cartId) throws ObjectNotFoundException;

    void deleteSubmittedCart();
}
