package bg.obag.obag.web;

import bg.obag.obag.model.service.CartServiceModel;
import bg.obag.obag.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BaseController {
    private final CartService cartService;

    public BaseController(CartService cartService) {
        this.cartService = cartService;
    }

//    @ModelAttribute("cartServiceModel")
//    CartServiceModel cartServiceModel() {
//        return new CartServiceModel();
//    }
//
//    @GetMapping("/cart")
//    public String getToCart(Model model,
//                            @CookieValue(name = "obag-cart", required = false) String cart) {
//        if (cart != null) {
//            CartServiceModel cartServiceModel = cartService.findById(Long.parseLong(cart));
//            model.addAttribute("cartServiceModel", cartServiceModel)
//                    .addAttribute("grandTotal", cartServiceModel.getTotalValue()
//                            .add(cartServiceModel.getDeliveryCost()));
//        }
//        return "cart";
//    }
}
