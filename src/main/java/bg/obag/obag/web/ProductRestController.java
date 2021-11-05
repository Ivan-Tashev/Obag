package bg.obag.obag.web;

import bg.obag.obag.model.view.ProductViewModel;
import bg.obag.obag.service.ProductsService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductRestController {
    private final ProductsService productsService;
    private final ModelMapper modelMapper;

    public ProductRestController(ProductsService productsService, ModelMapper modelMapper) {
        this.productsService = productsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductViewModel>> getAllProducts() {
        return ResponseEntity.ok().body(productsService.findAllOrderByCategory().stream()
                .map(productServiceModel -> modelMapper.map(productServiceModel, ProductViewModel.class))
                .collect(Collectors.toList()));
    }
}
