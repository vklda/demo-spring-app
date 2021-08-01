package github.com.vklda.spring_demo_app.product.web.controller;

import github.com.vklda.spring_demo_app.product.dto.ProductParam;
import github.com.vklda.spring_demo_app.product.model.Product;
import github.com.vklda.spring_demo_app.product.service.ProductService;
import github.com.vklda.spring_demo_app.product.web.converter.ProductWebConverter;
import github.com.vklda.spring_demo_app.product.web.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService productService;
    private final ProductWebConverter productWebConverter;

    @NotNull
    @PostMapping
    public ProductResponse create(@Valid @NotNull @RequestBody ProductParam productParam) {
        var product = productService.create(productParam);
        return productWebConverter.toResponse(product);
    }

    @GetMapping("/findAll")
    public Collection<ProductResponse> findAll() {
        return productService.findAll().stream()
                .map(productWebConverter::toResponse)
                .collect(Collectors.toSet());
    }

    @GetMapping("/find")
    public Collection<ProductResponse> findByName(@RequestParam("name") String name) {
        return productService.findByName(name).stream()
                .map(productWebConverter::toResponse)
                .collect(Collectors.toSet());
    }

    @GetMapping("/find/{id}")
    public ProductResponse findById(@Valid @NotNull @PathVariable Long id) {
        return productWebConverter.toResponse(productService.findById(id));
    }

}
