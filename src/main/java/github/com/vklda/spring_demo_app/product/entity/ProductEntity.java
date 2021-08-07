package github.com.vklda.spring_demo_app.product.entity;

import github.com.vklda.spring_demo_app.product.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Builder
@Table(name = "products")
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Nullable
    private String description;

    @NotNull
    @PositiveOrZero
    private BigDecimal discount;
}
