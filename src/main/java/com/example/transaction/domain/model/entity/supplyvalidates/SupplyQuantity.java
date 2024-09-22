package com.example.transaction.domain.model.entity.supplyvalidates;

import com.example.transaction.domain.model.exception.SupplyException;
import lombok.Getter;

@Getter
public class SupplyQuantity {
    public static final int ZERO_CONSTANT = 0;
    public static final String QUANTITY_MANDATORY = "quantity is mandatory";
    public static final String QUANTITY_MESSAGE_MIN_ERROR = "Quantity must be greater than zero";
    int quantity;
    private SupplyQuantity(int quantity) {
        toValidQuantity(quantity);
        this.quantity = quantity;
    }
    public static SupplyQuantity of (int quantity){
        toValidQuantity(quantity);
        return new SupplyQuantity(quantity);
    }
    private static void toValidQuantity(int quantity) {
        if(String.valueOf(quantity) == null || String.valueOf(quantity).isEmpty())
            throw new SupplyException(QUANTITY_MANDATORY);
        if (quantity < ZERO_CONSTANT) {
            throw new SupplyException(QUANTITY_MESSAGE_MIN_ERROR);
        }
    }
}
