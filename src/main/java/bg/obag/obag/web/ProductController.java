package bg.obag.obag.web;

import bg.obag.obag.model.binding.ProductAddBindingModel;
import bg.obag.obag.model.entity.enums.Category;
import bg.obag.obag.model.entity.enums.Season;
import bg.obag.obag.model.service.ProductServiceModel;
import bg.obag.obag.model.view.ProductViewModel;
import bg.obag.obag.service.ProductsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductsService productsService;
    private final ModelMapper modelMapper;

    public ProductController(ProductsService productsService, ModelMapper modelMapper) {
        this.productsService = productsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/import")
    public String importJson() throws IOException {
        productsService.importProducts();
        return "redirect:/";
    }

    @GetMapping("/add")
    public String getAddProductPage(Model model) {
        if (!model.containsAttribute("productAddBindingModel")) {
            model.addAttribute("productAddBindingModel", new ProductAddBindingModel());
        }
        if (!model.containsAttribute("successfullyAddedProduct")){
            model.addAttribute("successfullyAddedProduct", false);
        }
        model.addAttribute("categories", Category.values());
        model.addAttribute("seasons", Season.values());
        model.addAttribute("allProducts", productsService.findAllOrderByCategory());
        return "addProduct";

    }

    @PostMapping("/add")
    public String addProduct(@Valid ProductAddBindingModel productAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes,
                             Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);
            return "redirect:/products/add";
        }
        // ADD/SAVE NEW PRODUCT INTO DB
        productsService.addProduct(productAddBindingModel, principal);
        // if added/saved show the Success green alert message.
        redirectAttributes.addFlashAttribute("successfullyAddedProduct", true);
        return "redirect:/products/add";
    }

    @GetMapping("/{id}")
    public String getProductPage(@PathVariable Long id, Model model) {
        ProductServiceModel productServiceModel = productsService.findProductById(id);
        model.addAttribute("product",
                modelMapper.map(productServiceModel, ProductViewModel.class));
        return "product";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        productsService.deleteById(id);
        return "redirect:/products/add";
    }

}
