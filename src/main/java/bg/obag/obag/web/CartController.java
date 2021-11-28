package bg.obag.obag.web;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.binding.UserUpdateBindModel;
import bg.obag.obag.model.service.CartServiceModel;
import bg.obag.obag.service.CartService;
import bg.obag.obag.service.ProductsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final ProductsService productsService;
    private final CartService cartService;

    public CartController(ProductsService productsService, CartService cartService) {
        this.productsService = productsService;
        this.cartService = cartService;
    }

    @ModelAttribute("userUpdateBindModel")
    UserUpdateBindModel userUpdateBindModel() {
        return new UserUpdateBindModel();
    }

    @ModelAttribute("cartServiceModel")
    CartServiceModel cartServiceModel() {
        return new CartServiceModel();
    }

    @GetMapping
    public String getToCart(Model model,
                            @CookieValue(name = "obag-cart", required = false) String cart) {
        if (cart != null) {
            CartServiceModel cartServiceModel = cartService.findById(Long.parseLong(cart));
            model.addAttribute("cartServiceModel", cartServiceModel)
                    .addAttribute("grandTotal", cartServiceModel.getTotalValue()
                            .add(cartServiceModel.getDeliveryCost()));
        }
        return "cart";
    }

    @PostMapping("{id}")
    public String addToCart(@PathVariable("id") Long productId,
                            RedirectAttributes redirectAttributes,
                            @CookieValue(name = "obag-cart", required = false) String cart,
                            HttpServletResponse response,
                            @AuthenticationPrincipal UserDetails principal) throws ProductNotFoundException {
        CartServiceModel cartServiceModel;
        if (cart == null) {
            cartServiceModel = cartService.createCart(productId, principal);
        } else {
            cartServiceModel = cartService.findById(Long.parseLong(cart));
            cartService.addProductToCart(cartServiceModel.getId(), productId);
        }

        Cookie cookie = new Cookie("obag-cart", String.valueOf(cartServiceModel.getId()));
        response.addCookie(cookie);

        redirectAttributes.addFlashAttribute("success", true);

        if (principal == null)
            return "redirect:/users/login";
        else
            return "redirect:/products/" + productId;
    }

    @DeleteMapping("{id}")
    public String deleteFromCart(@PathVariable("id") Long productId,
                                 @CookieValue(name = "obag-cart", required = false) String cart) throws ProductNotFoundException {
        cartService.deleteProductFromCart(Long.parseLong(cart), productId);
        return "redirect:/cart";
    }
}
