package github.com.vklda.spring_demo_app.product.repository;

import github.com.vklda.spring_demo_app.product.entity.ProductEntity;
import github.com.vklda.spring_demo_app.product.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Collection;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Collection<ProductEntity> findByName(@NotNull String name);

    Collection<ProductEntity> findByType(@NotNull ProductType type);
}
