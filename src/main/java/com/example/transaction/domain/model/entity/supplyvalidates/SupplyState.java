package com.example.transaction.domain.model.entity.supplyvalidates;

import com.example.transaction.domain.model.exception.SupplyException;
import lombok.Getter;


@Getter
public class SupplyState {
    private static final int MAXIMUM_ALLOW_LETTERS = 50;
    private static final String MESSAGE_MANDATORY = "state is mandatory";
    private static final String MESSAGE_MAX_BIGGER = "state don't be bigger than 10 characters";

    String state;

    private SupplyState(String state) {
        this.state = state;
    }

    public static SupplyState of(String state) {
        toValidState(state);
        return new SupplyState(state);
    }

    private static void toValidState(String state){
        if(state == null || state.isEmpty())
            throw new SupplyException(MESSAGE_MANDATORY);
        if(state.length() > MAXIMUM_ALLOW_LETTERS)
            throw new SupplyException(MESSAGE_MAX_BIGGER);
    }
}
