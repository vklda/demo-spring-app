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

import java.util.Collection;
import java.util.Objects;
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
    @Transactional
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
    public Product update(Long id, ProductParam productParam) {
        var productEntity = productRepository.getById(id);

        productEntity.setName(productParam.getName());
        productEntity.setType(productParam.getType());
        productEntity.setPrice(productParam.getPrice());
        productEntity.setDescription(productParam.getDescription());
        productEntity.setDiscount(productParam.getDiscount());

        var savedProductEntity = productRepository.save(productEntity);
        return productEntityConverter.fromEntityToModel(savedProductEntity);
    }
}
