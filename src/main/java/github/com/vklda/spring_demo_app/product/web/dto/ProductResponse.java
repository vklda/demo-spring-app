package github.com.vklda.spring_demo_app.product.web.dto;

import github.com.vklda.spring_demo_app.product.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    @NotNull
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

    @NotNull
    private BigDecimal actualPrice;
}
