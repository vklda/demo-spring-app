package github.com.vklda.spring_demo_app.product.service;

import github.com.vklda.spring_demo_app.product.dto.ProductParam;
import github.com.vklda.spring_demo_app.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface ProductService {

    Product create(ProductParam productParam);

    Collection<Product> findAll();

    Collection<Product> findByName(String name);

    Product findById(Long id);
}