package bg.obag.obag.web;

import bg.obag.obag.exception.CategoryNotFoundException;
import bg.obag.obag.model.binding.CategoryBindModel;
import bg.obag.obag.model.service.CategoryServiceModel;
import bg.obag.obag.model.service.ProductServiceModel;
import bg.obag.obag.model.view.ProductCategoryViewModel;
import bg.obag.obag.service.CategoryService;
import bg.obag.obag.service.ProductsService;
import org.modelmapper.ModelMapper;
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

    public CategoryController(ProductsService productsService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productsService = productsService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{category}")
    public String getCategoryPage(@PathVariable String category, Model model) throws CategoryNotFoundException {
        List<ProductServiceModel> productServiceModelList = productsService.findByCategory(category);

        List<ProductCategoryViewModel> productCategoryViewModels = productServiceModelList.stream()
                .map(productServiceModel -> modelMapper.map(productServiceModel, ProductCategoryViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("productsInCategory", productCategoryViewModels);
        return "category";
    }

// -------------------------------------- ADD CATEGORY ---------------------------------------------

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
                              Principal principal) {
        // check for @Valid Input or Unique category
        if (bindingResult.hasErrors() || categoryService.existsCategory(categoryBindModel.getCategory())) {
            redirectAttributes.addFlashAttribute("categoryBindModel", categoryBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.categoryBindModel", bindingResult);
            if (categoryService.existsCategory(categoryBindModel.getCategory())) {
                redirectAttributes.addFlashAttribute("categoryExist", true);
            }
            return "redirect:/category/add";
        }
        categoryService.addCategory(categoryBindModel, principal);
        // if added show the Success green alert message.
        redirectAttributes.addFlashAttribute("successfullyAddedCategory", true);
        return "redirect:/category/add";
    }

    // -------------------------------------- UPDATE PRODUCT ---------------------------------------------

    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable Long id, Model model) throws CategoryNotFoundException {
        CategoryServiceModel categoryServiceModel = categoryService.findById(id);
        model.addAttribute("categoryBindModel", categoryServiceModel)
                .addAttribute("allCategories", categoryService.findAllOrderByPriorityAsc());
        return "updateCategory";
    }

    @PatchMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @Valid CategoryBindModel categoryBindModel,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                 Principal principal) throws CategoryNotFoundException {
        // check for @Valid Input or Unique category
        if (bindingResult.hasErrors() || categoryService.existsCategoryExceptId(categoryBindModel.getCategory(), id)) {
            redirectAttributes.addFlashAttribute("categoryBindModel", categoryBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.categoryBindModel", bindingResult);
            if (categoryService.existsCategoryExceptId(categoryBindModel.getCategory(), id)) {
                redirectAttributes.addFlashAttribute("categoryExist", true);
            }
            return "redirect:/category/edit/" + id;
        }
        categoryService.updateCategory(categoryBindModel, principal);
        // if added show the Success green alert message.
        redirectAttributes.addFlashAttribute("successfullyAddedCategory", true);
        return "redirect:/category/edit/" + id;
    }

}
