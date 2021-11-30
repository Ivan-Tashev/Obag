package bg.obag.obag.web;

import bg.obag.obag.exception.CategoryNotFoundException;
import bg.obag.obag.model.binding.CategoryBindModel;
import bg.obag.obag.model.service.CategoryServiceModel;
import bg.obag.obag.model.service.ProductServiceModel;
import bg.obag.obag.model.view.ProductCategoryViewModel;
import bg.obag.obag.service.CategoryService;
import bg.obag.obag.service.ProductsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final ProductsService productsService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final CartController cartController;

    public CategoryController(ProductsService productsService, CategoryService categoryService, ModelMapper modelMapper, CartController cartController) {
        this.productsService = productsService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.cartController = cartController;
    }

    /* ----------------------------------- ROLE_USER CATEGORY VIEW -------------------------------------- */

    @GetMapping("/{category}")
    public String getCategoryPage(@PathVariable String category,
                                  Model model,
                                  @CookieValue(name = "obag-cart", required = false) String cart,
                                  @AuthenticationPrincipal UserDetails principal) throws CategoryNotFoundException {
        List<ProductServiceModel> productServiceModelList = productsService.findByCategory(category);

        List<ProductCategoryViewModel> productCategoryViewModels = productServiceModelList.stream()
                .map(productServiceModel -> modelMapper.map(productServiceModel, ProductCategoryViewModel.class))
                .collect(Collectors.toList());

        CategoryServiceModel categoryServiceModel = categoryService.findByCategory(category);

        cartController.getCart(model, cart, principal);

        model.addAttribute("productsInCategory", productCategoryViewModels)
                .addAttribute("categoryImage", categoryServiceModel.getImage());
        return "category";
    }

    /* --------------------------------- ROLE_ADMIN ADD/EDIT CATEGORY ---------------------------------- */

    @ModelAttribute("categoryBindModel")
    public CategoryBindModel categoryBindModel() {
        return new CategoryBindModel();
    }

    @GetMapping("/add")
    public String getAddCategoryPage(Model model) {
        model.addAttribute("allCategories", categoryService.findAllOrderByPriorityAsc());
        return "addCategory";
    }

    @PostMapping("/add")
    public String addCategory(@Valid CategoryBindModel categoryBindModel,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              Principal principal) throws CategoryNotFoundException {
        // check for @Valid Input or Unique category
        String categoryName = categoryBindModel.getCategory();
        Long categoryId = categoryBindModel.getId();
        if (bindingResult.hasErrors()
                || categoryService.existsCategoryExceptId(categoryName, categoryId)
                || (categoryService.existsCategory(categoryName) && categoryId == null)) {
            redirectAttributes.addFlashAttribute("categoryBindModel", categoryBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.categoryBindModel", bindingResult);
            if (categoryService.existsCategoryExceptId(categoryName, categoryId)
                    || (categoryService.existsCategory(categoryName) && categoryId == null)) {
                redirectAttributes.addFlashAttribute("categoryExist", true);
            }
            return "redirect:/category/add";
        }
        categoryService.addEditCategory(modelMapper.map(categoryBindModel, CategoryServiceModel.class), principal);
        // if added show the Success green alert message.
        redirectAttributes.addFlashAttribute("successfullyAddedCategory", true);
        return "redirect:/category/add";
    }

    @GetMapping("/edit/{id}")
    public String getEditCategoryPage(@PathVariable Long id, Model model) throws CategoryNotFoundException {
        model.addAttribute("categoryBindModel", categoryService.findById(id))
                .addAttribute("allCategories", categoryService.findAllOrderByPriorityAsc());
        return "addCategory";
    }
}