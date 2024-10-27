package com.example.transaction.domain.model.entity.supplyvalidates;

import com.example.transaction.domain.model.exception.SupplyException;
import lombok.Getter;

import static com.example.transaction.domain.model.constant.SupplyConstant.*;


@Getter
public class SupplyState {
    String state;

    private SupplyState(String state) {
        this.state = state;
    }

    public static SupplyState of(String state) {
        return new SupplyState(toValidState(state));
    }

    private static String toValidState(String state){
        if(state == null || state.isEmpty())
            throw new SupplyException(MESSAGE_MANDATORY);
        String stateTrim = state.trim();
        if(stateTrim.length() > MAXIMUM_ALLOW_LETTERS)
            throw new SupplyException(MESSAGE_MAX_BIGGER);
        return stateTrim;

    }
}
