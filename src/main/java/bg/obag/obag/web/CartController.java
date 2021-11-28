package bg.obag.obag.web;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.binding.UserUpdateBindModel;
import bg.obag.obag.model.service.CartServiceModel;
import bg.obag.obag.service.CartService;
import bg.obag.obag.service.ProductsService;
import bg.obag.obag.service.UserService;
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
    private final CartService cartService;
    private final UserService userService;

    public CartController(ProductsService productsService, CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
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
    public String getCart(Model model,
                          @CookieValue(name = "obag-cart", required = false) String cart,
                          @AuthenticationPrincipal UserDetails principal) {
        if (cart != null) {
            CartServiceModel cartServiceModel = cartService.findById(Long.parseLong(cart));
            model.addAttribute("cartServiceModel", cartServiceModel)
                    .addAttribute("grandTotal", cartServiceModel.getTotalValue().add(cartServiceModel.getDeliveryCost()));

            if (cartServiceModel.getUser() != null)
                model.addAttribute("userUpdateBindModel", cartServiceModel.getUser());
        }
        if (principal != null)
            model.addAttribute("userUpdateBindModel", userService.findCurrentUserByEmail(principal.getUsername()));
        return "cart";
    }

    @PostMapping("{id}")
    public String addToCart(@PathVariable("id") Long productId,
                            @RequestParam Integer qty,
                            RedirectAttributes redirectAttributes,
                            @CookieValue(name = "obag-cart", required = false) String cart,
                            HttpServletResponse response,
                            @AuthenticationPrincipal UserDetails principal) throws ProductNotFoundException {
        CartServiceModel cartServiceModel;
        if (cart == null) {
            cartServiceModel = cartService.createCart(productId, principal);
        } else {
            cartServiceModel = cartService.findById(Long.parseLong(cart));
            cartService.addProductToCart(cartServiceModel.getId(), productId, principal);
        }
        // TODO: handle QTY (more than one of same product)

        Cookie cookie = new Cookie("obag-cart", String.valueOf(cartServiceModel.getId()));
        cookie.setPath("/");
        cookie.setMaxAge(7_776_000); // 90 days
        response.addCookie(cookie);

        redirectAttributes.addFlashAttribute("success", true);

        return "redirect:/products/" + productId;
    }

    @DeleteMapping("{id}")
    public String deleteFromCart(@PathVariable("id") Long productId,
                                 @CookieValue(name = "obag-cart", required = false) String cart) throws ProductNotFoundException {
        cartService.deleteProductFromCart(Long.parseLong(cart), productId);
        return "redirect:/cart";
    }
}
