package github.com.vklda.spring_demo_app.product.service.converter;

import github.com.vklda.spring_demo_app.product.entity.ProductEntity;
import github.com.vklda.spring_demo_app.product.dto.ProductParam;
import github.com.vklda.spring_demo_app.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductEntityConverter {
    private final ModelMapper modelMapper;

    public Product toModel(ProductParam productParam) {
        return modelMapper.map(productParam, Product.class);
    }

    public ProductEntity toEntity(Product product) {
        return modelMapper.map(product, ProductEntity.class);
    }

    public Product fromEntityToModel(ProductEntity productEntity) {
        return modelMapper.map(productEntity, Product.class);
    }

    public void updateFields(Product updatedProduct, Product oldProduct) {
        updatedProduct.setId(oldProduct.getId());
        modelMapper.map(updatedProduct, oldProduct);
    }

    public Collection<Product> toModels(Collection<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(this::fromEntityToModel)
                .collect(Collectors.toList());
    }
}
