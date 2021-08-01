package github.com.vklda.spring_demo_app.product.web.converter;

import github.com.vklda.spring_demo_app.product.model.Product;
import github.com.vklda.spring_demo_app.product.web.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductWebConverter {

    private final ModelMapper modelMapper;

    public ProductResponse toResponse(Product product) {
        var actualPrice = product.getPrice().subtract(
                product.getPrice().multiply(product.getDiscount()).divide(BigDecimal.valueOf(100)));
        var productResponse = modelMapper.map(product, ProductResponse.class);
        productResponse.setActualPrice(actualPrice);
        return productResponse;
    }
}
