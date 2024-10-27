package com.example.transaction.domain.model.entity.supplyvalidates;
import com.example.transaction.domain.model.exception.SupplyException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import static com.example.transaction.domain.model.constant.SupplyConstant.*;

@NoArgsConstructor
@Getter
public class SupplyPrice {
    BigDecimal price;
    private SupplyPrice(BigDecimal price){
        this.price = price;
    }

    public static SupplyPrice of(BigDecimal price) {
        toValidPrice(price);
        return new SupplyPrice(price);
    }

    private static void toValidPrice(BigDecimal price) {
        if (price != null &&
                price.compareTo(BigDecimal.ZERO) < ZERO_CONSTANT) {
            throw new SupplyException(PRICE_MIN_ZERO);
        }
    }

}
