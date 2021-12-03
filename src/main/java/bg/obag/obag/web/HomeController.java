package bg.obag.obag.web;

import bg.obag.obag.service.UserService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class HomeController {
    private final UserService userService;
    private final CartController cartController;

    public HomeController(UserService userService, CartController cartController) {
        this.userService = userService;
        this.cartController = cartController;
    }

    @ModelAttribute("user")
    public String getFirstName(Principal principal) {
        return principal == null ? "" :
                userService.findCurrentUserByEmail(principal.getName()).getFirstName();
    }

    @GetMapping("/")
    public String getHome(Model model,
                          @CookieValue(name = "obag-cart", required = false) String cart,
                          @AuthenticationPrincipal UserDetails principal) {

        cartController.getCart(model, cart, principal);

        return "index";
    }
}
