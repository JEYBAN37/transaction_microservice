package com.example.transaction.domain.model.entity.salesvalidates;

import com.example.transaction.domain.model.exception.SupplyException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import static com.example.transaction.domain.model.constant.SupplyConstant.*;

@Getter
@Setter
@NoArgsConstructor
public class ArticleQuantity {
    Integer quantity;

    private ArticleQuantity(Integer quantity){
        this.quantity = quantity;
    }
    public static ArticleQuantity of (Integer quantity) {
        toValidQuantity(quantity);
        return new ArticleQuantity(quantity);
    }

    private static void toValidQuantity(Integer quantity) {
        if( quantity == null )
            throw new SupplyException(QUANTITY_MANDATORY);
        if (quantity < ZERO_CONSTANT)
            throw new SupplyException(QUANTITY_MESSAGE_MIN_ERROR);
    }
}
