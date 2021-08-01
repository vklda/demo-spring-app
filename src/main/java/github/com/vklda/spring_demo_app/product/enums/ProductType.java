package github.com.vklda.spring_demo_app.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductType {
    MILK("MILK"),
    FRUIT("FRUIT"),
    VEGETABLE("VEGETABLE"),
    MEAT("MEAT"),
    FISH("FISH");

    String name;
}
