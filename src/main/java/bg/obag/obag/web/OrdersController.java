package bg.obag.obag.web;

import bg.obag.obag.model.binding.UserOrderBindModel;
import bg.obag.obag.model.service.CartServiceModel;
import bg.obag.obag.model.service.OrderServiceModel;
import bg.obag.obag.service.CartService;
import bg.obag.obag.service.OrderService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;
    private final CartService cartService;

    public OrdersController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }


    /* -------------------------------------- ROLE_ADMIN ORDERS ----------------------------------------------- */



    /* -------------------------------------- ROLE_USER ORDERS ----------------------------------------------- */

    @GetMapping()
    public String getOrdersPage(Model model,
                                @AuthenticationPrincipal UserDetails principal) {
        List<OrderServiceModel> allOrdersForUser = orderService.findAllOrdersForUser(principal);
        model.addAttribute("allOrdersForUser", allOrdersForUser);
        return "orders";
    }

    @PostMapping()
    public String submitOrder(@RequestParam Long cartId,
                              @RequestParam String payment,
                              @RequestParam String delivery,
                              @Valid UserOrderBindModel userOrderBindModel, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @CookieValue(name = "obag-cart", required = false) String cart,
                              HttpServletResponse response) throws ObjectNotFoundException {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userOrderBindModel", userOrderBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userOrderBindModel", bindingResult);
            return "redirect:/cart";
        }
        OrderServiceModel orderServiceModel= orderService.createOrder(cartId, payment, delivery, userOrderBindModel);

        cartService.deleteSubmittedCart(cartId);

        Cookie cookie = new Cookie("obag-cart", cart);
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete cookie
        response.addCookie(cookie);

        return "redirect:/orders";
    }

    /* ------------------------------------ EXCEPTION HANDLER ------------------------------------------------- */

}
