package bg.obag.obag.web;

import bg.obag.obag.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final ProductsService productsService;

    public CategoryController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/{category}")
    public String getCategoryPage(@PathVariable String category, Model model) {
        model.addAttribute("productsInCategory", productsService.findByCategory(category));
        return "category";
    }
}
