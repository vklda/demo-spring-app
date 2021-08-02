package github.com.vklda.spring_demo_app.product.web.controller;

import github.com.vklda.spring_demo_app.product.dto.ProductParam;
import github.com.vklda.spring_demo_app.product.enums.ProductType;
import github.com.vklda.spring_demo_app.product.service.ProductService;
import github.com.vklda.spring_demo_app.product.web.converter.ProductWebConverter;
import github.com.vklda.spring_demo_app.product.web.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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

    @GetMapping()
    public Collection<ProductResponse> findByName(@NotNull @RequestParam("name") String name) {
        return productService.findByName(name).stream()
                .map(productWebConverter::toResponse)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@Valid @NotNull @PathVariable Long id) {
        return productWebConverter.toResponse(productService.findById(id));
    }

    @GetMapping("/type/{type}")
    public Collection<ProductResponse> findByType(@Valid @NotNull @PathVariable String type) {
        return productService.findByType(ProductType.valueOf(type)).stream()
                .map(productWebConverter::toResponse)
                .collect(Collectors.toSet());
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @NotNull @RequestBody ProductParam productParam) {
        var updatedProduct = productService.update(id, productParam);
        return productWebConverter.toResponse(updatedProduct);
    }

    @PutMapping("/type/{type}")
    public Collection<ProductResponse> updateDiscountByType(@Valid @NotNull @PathVariable String type,
                                                            @NotNull @RequestParam("discount") BigDecimal discount) {
        return productService.updateDiscountByType(ProductType.valueOf(type), discount).stream()
                .map(productWebConverter::toResponse)
                .collect(Collectors.toSet());
    }
}
