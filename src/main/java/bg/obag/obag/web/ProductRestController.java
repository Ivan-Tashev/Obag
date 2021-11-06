package bg.obag.obag.web;

import bg.obag.obag.exception.ProductNotFoundException;
import bg.obag.obag.model.service.ProductServiceModel;
import bg.obag.obag.model.view.ProductViewModel;
import bg.obag.obag.service.ProductsService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<ProductViewModel> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        ProductServiceModel productServiceModel = productsService.findProductById(id);
        return ResponseEntity.ok().body(modelMapper.map(productServiceModel, ProductViewModel.class));
    }

    @Secured("ROLE_SUPERADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductViewModel> deleteProductById(@PathVariable Long id) {
        productsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
