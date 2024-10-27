package com.example.transaction.domain.model.entity.salesvalidates;

import com.example.transaction.domain.model.exception.SupplyException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.example.transaction.domain.model.constant.SupplyConstant.*;


@NoArgsConstructor
@Getter
public class ArticlePrice {
    BigDecimal price;
    private ArticlePrice(BigDecimal price){
        this.price = price;
    }

    public static ArticlePrice of(BigDecimal price) {
        toValidPrice(price);
        return new ArticlePrice(price);
    }

    private static void toValidPrice(BigDecimal price) {
        if(price == null)
            throw new SupplyException(PRICE_MANDATORY);
        if (price.compareTo(BigDecimal.ZERO) < ZERO_CONSTANT) {
            throw new SupplyException(PRICE_MIN_ZERO);
        }
    }

}
