package bg.obag.obag.web;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.binding.ProductAddBindingModel;
import bg.obag.obag.model.binding.ProductUpdateBindingModel;
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

//-------------------------------------- IMPORT PRODUCTS -------------------------------------------

    @GetMapping("/import")
    public String importJson() throws IOException {
        productsService.importProducts();
        return "redirect:/";
    }

// -------------------------------------- ADD PRODUCT ---------------------------------------------

    @ModelAttribute("productAddBindingModel")
    ProductAddBindingModel productAddBindingModel() {
        return new ProductAddBindingModel();
    }

    @GetMapping("/add")
    public String getAddProductPage(Model model) {
        if (!model.containsAttribute("successfullyAddedProduct")) {
            model.addAttribute("successfullyAddedProduct", false);
        }
        model.addAttribute("allProducts", productsService.findAllOrderByCategory());
        return "addProduct";

    }

    @PostMapping("/add")
    public String addProduct(@Valid ProductAddBindingModel productAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes,
                             Principal principal) throws ProductNotFoundException {
        if (bindingResult.hasErrors()
                || productsService.checkNameExists(productAddBindingModel.getName())
                || productsService.checkSkuExists(productAddBindingModel.getSku())
                || productsService.checkBarcodeExists(productAddBindingModel.getBarcode())) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);
            // check unique name
            if (productsService.checkNameExists(productAddBindingModel.getName())) {
                redirectAttributes.addFlashAttribute("productNameExist", true);
            }
            // check unique sku
            if (productsService.checkSkuExists(productAddBindingModel.getSku())) {
                redirectAttributes.addFlashAttribute("productSkuExist", true);
            }
            // check unique barcode
            if (productsService.checkBarcodeExists(productAddBindingModel.getBarcode())) {
                redirectAttributes.addFlashAttribute("productBarcodeExist", true);
            }
            return "redirect:/products/add";
        }
        // ADD a PRODUCT INTO DB
        productsService.addProduct(productAddBindingModel, principal);
        // if added show the Success green alert message.
        redirectAttributes.addFlashAttribute("successfullyAddedProduct", true);
        return "redirect:/products/add";
    }

// -------------------------------------- CLONE PRODUCT --------------------------------------------

    @GetMapping("/clone/{id}")
    public String clone(@PathVariable Long id, Model model) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = productsService.findProductById(id);
        ProductAddBindingModel productAddBindingModel = modelMapper.map(productServiceModel, ProductAddBindingModel.class)
                .setId(null) // NOT TO OVERWRITE EXISTING PRODUCT
                .setCategory(productServiceModel.getCategory().name())
                .setSeason(productServiceModel.getSeason().name());
        model.addAttribute("productAddBindingModel", productAddBindingModel)
                .addAttribute("allProducts", productsService.findAllOrderByCategory());
        return "addProduct";
    }

// -------------------------------------- UPDATE PRODUCT ---------------------------------------------

    @ModelAttribute("productUpdateBindingModel")
    ProductUpdateBindingModel productUpdateBindingModel() {
        return new ProductUpdateBindingModel();
    }

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = productsService.findProductById(id);
        ProductUpdateBindingModel productUpdateBindingModel = modelMapper.map(productServiceModel, ProductUpdateBindingModel.class)
                .setCategory(productServiceModel.getCategory().name())
                .setSeason(productServiceModel.getSeason().name());
        model.addAttribute("productUpdateBindingModel", productUpdateBindingModel)
                .addAttribute("allProducts", productsService.findAllOrderByCategory());
        return "updateProduct";
    }

    @PutMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid ProductUpdateBindingModel productUpdateBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                Principal principal) throws ProductNotFoundException {
        if (bindingResult.hasErrors()
                || productsService.checkNameExistsExceptId(productUpdateBindingModel.getName(), id)
                || productsService.checkSkuExistsExceptId(productUpdateBindingModel.getSku(), id)
                || productsService.checkBarcodeExistsExceptId(productUpdateBindingModel.getBarcode(), id)) {
            redirectAttributes.addFlashAttribute("productUpdateBindingModel", productUpdateBindingModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.productUpdateBindingModel", bindingResult);
            // check unique name
            if (productsService.checkNameExistsExceptId(productUpdateBindingModel.getName(), id)) {
                redirectAttributes.addFlashAttribute("productNameExist", true);
            }
            // check unique sku
            if (productsService.checkSkuExistsExceptId(productUpdateBindingModel.getSku(), id)) {
                redirectAttributes.addFlashAttribute("productSkuExist", true);
            }
            // check unique barcode
            if (productsService.checkBarcodeExistsExceptId(productUpdateBindingModel.getBarcode(), id)) {
                redirectAttributes.addFlashAttribute("productBarcodeExist", true);
            }
            return "redirect:/products/edit/" + id;
        }
        // UPDATE the PRODUCT INTO DB
        productsService.updateProduct(productUpdateBindingModel, principal);
        // if updated show the Success green alert message.
        redirectAttributes.addFlashAttribute("successfullyAddedProduct", true);
        return "redirect:/products/edit/" + id;
    }


    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        productsService.deleteById(id);
        return "redirect:/products/add";
    }

//---------------------------------  PRODUCT PAGE for ALL USERS -------------------------------------

    @GetMapping("/{id}")
    public String getProductPage(@PathVariable Long id, Model model) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = productsService.findProductById(id);
        model.addAttribute("product",
                modelMapper.map(productServiceModel, ProductViewModel.class));
        return "product";
    }
}
