package bg.obag.obag.web;

import bg.obag.obag.model.binding.UserUpdateBindModel;
import bg.obag.obag.service.CategoryService;
import bg.obag.obag.service.ProductsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final ProductsService productsService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    private List<Long> productIds = new ArrayList<>();

    public CartController(ProductsService productsService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productsService = productsService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("userUpdateBindModel")
    UserUpdateBindModel userUpdateBindModel() {
        return new UserUpdateBindModel();
    }

    @GetMapping
    public String getToCart(Model model,
                            @CookieValue(name = "cart", required = false) String cart) {
            model.addAttribute("productsInCart", productsService.findByCookie(cart))
                    .addAttribute("sum", productsService.findByCookie(cart).stream()
                            .mapToDouble(product -> product.getPrice().doubleValue()).sum())
                    .addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("{id}")
    public String addToCart(@PathVariable Long id,
                            @CookieValue(name = "cart", required = false) String cart,
                            HttpServletResponse response) {
        if (cart == null) {
            cart = id + "-";
        } else {
            cart += id + "-";
        }
        Cookie cookie = new Cookie("cart", cart);

        response.addCookie(cookie);
        return "redirect:/cart";
    }
}
