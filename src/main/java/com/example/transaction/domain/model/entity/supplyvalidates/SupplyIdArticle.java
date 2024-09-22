package com.example.transaction.domain.model.entity.supplyvalidates;

import com.example.transaction.domain.model.exception.SupplyException;
import lombok.Getter;

@Getter
public class SupplyIdArticle {
    public static final int ZERO_CONSTANT = 0;
    public static final String QUANTITY_MANDATORY = "id article is mandatory";
    public static final String QUANTITY_MESSAGE_MIN_ERROR = "id article must  be greater than zero";
    Long id;
    private SupplyIdArticle(Long id) {
        toValidQuantity(id);
        this.id = id;
    }
    public static SupplyIdArticle of (Long id){
        toValidQuantity(id);
        return new SupplyIdArticle(id);
    }
    private static void toValidQuantity(Long id) {
        if(String.valueOf(id) == null || String.valueOf(id).isEmpty())
            throw new SupplyException(QUANTITY_MANDATORY);
        if (id < ZERO_CONSTANT) {
            throw new SupplyException(QUANTITY_MESSAGE_MIN_ERROR);
        }
    }
}
