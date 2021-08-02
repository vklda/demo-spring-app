package github.com.vklda.spring_demo_app.product.service;

import github.com.vklda.spring_demo_app.exceptions.ProductNotFoundException;
import github.com.vklda.spring_demo_app.product.Entity.ProductEntity;
import github.com.vklda.spring_demo_app.product.dto.ProductParam;
import github.com.vklda.spring_demo_app.product.enums.ProductType;
import github.com.vklda.spring_demo_app.product.model.Product;
import github.com.vklda.spring_demo_app.product.repository.ProductRepository;
import github.com.vklda.spring_demo_app.product.service.converter.ProductEntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductEntityConverter productEntityConverter;

    @Override
    @Transactional
    public Product create(ProductParam productParam) {
        log.info("Creating product");

        var product = productEntityConverter.toModel(productParam);
        var productEntity = productEntityConverter.toEntity(product);
        var savedProductEntity = productRepository.save(productEntity);

        log.info("Saving product with id: " + savedProductEntity.getId() + " and name is " + savedProductEntity.getName());
        return productEntityConverter.fromEntityToModel(savedProductEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Product> findAll() {
        return productRepository.findAll().stream()
                .filter(Objects::nonNull)
                .map(productEntityConverter::fromEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Product> findByName(String name) {
        return productRepository.findByName(name).stream()
                .map(productEntityConverter::fromEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Product findById(Long id) {
        var productEntityOpt = productRepository.findById(id);
        if (productEntityOpt.isPresent()) {
            return productEntityConverter.fromEntityToModel(productEntityOpt.get());
        }
        throw new ProductNotFoundException(String.format("Product with id %d not found", id));
    }

    @Override
    public Collection<Product> findByType(ProductType type) {
        return productRepository.findByType(type).stream()
                .map(productEntityConverter::fromEntityToModel)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Product update(Long id, ProductParam productParam) {
        var oldProduct = productRepository.findById(id)
                .map(productEntityConverter::fromEntityToModel)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        var updatedProduct = productEntityConverter.toModel(productParam);
        productEntityConverter.updateFields(updatedProduct, oldProduct);

        var updatedProductEntity = productEntityConverter.toEntity(updatedProduct);
        var savedProductEntity = productRepository.save(updatedProductEntity);

        return productEntityConverter.fromEntityToModel(savedProductEntity);
    }

    @Override
    @Transactional
    public Collection<Product> updateDiscountByType(ProductType type, BigDecimal discount) {
        var products = findByType(type);

        products.forEach(it -> it.setDiscount(discount));

        var updatedEntities = products.stream()
                .map(productEntityConverter::toEntity)
                .collect(Collectors.toSet());

        var savedEntities = productRepository.saveAll(updatedEntities);

        return savedEntities.stream()
                .map(productEntityConverter::fromEntityToModel)
                .collect(Collectors.toSet());
    }
}
