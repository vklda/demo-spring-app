package github.com.vklda.spring_demo_app.product.repository;

import github.com.vklda.spring_demo_app.product.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Collection<ProductEntity> findByName(String name);

}
