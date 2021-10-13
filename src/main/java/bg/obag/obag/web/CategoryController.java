package bg.obag.obag.web;

import bg.obag.obag.model.service.ProductServiceModel;
import bg.obag.obag.model.view.ProductCategoryViewModel;
import bg.obag.obag.service.ProductsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final ProductsService productsService;
    private final ModelMapper modelMapper;

    public CategoryController(ProductsService productsService, ModelMapper modelMapper) {
        this.productsService = productsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{category}")
    public String getCategoryPage(@PathVariable String category, Model model) {
        List<ProductServiceModel> productServiceModelList = productsService.findByCategory(category);

        List<ProductCategoryViewModel> productCategoryViewModels = productServiceModelList.stream()
                .map(productServiceModel -> modelMapper.map(productServiceModel, ProductCategoryViewModel.class))
                .collect(Collectors.toList());

        model.addAttribute("productsInCategory", productCategoryViewModels);
        return "category";
    }
}
