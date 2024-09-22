package com.example.transaction.domain.model.entity;

import com.example.transaction.domain.model.dto.command.SupplyCreateCommand;
import com.example.transaction.domain.model.entity.supplyvalidates.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
public class Supply {
    private Long id;
    private SupplyIdArticle idArticle;
    private SupplyQuantity quantity;
    private SupplyState state;
    private BigDecimal price;
    private String date;

    public Supply(Long id, Long idArticle, int quantity, String state ,BigDecimal price, String date){
        this.id = id;
        this.idArticle = SupplyIdArticle.of(idArticle);
        this.quantity = SupplyQuantity.of(quantity);
        this.state = SupplyState.of(state);
        this.price = price;
        this.date = date;
    }
    public Supply requestToCreate(SupplyCreateCommand supplyCreateCommand)
    {
        this.idArticle = SupplyIdArticle.of(supplyCreateCommand.getIdArticle());
        this.quantity = SupplyQuantity.of(supplyCreateCommand.getQuantity());
        this.state = SupplyState.of(supplyCreateCommand.getState());
        this.date = supplyCreateCommand.getDate();
        return this;
    }

    public Long getIdArticle() {
        return idArticle.getId();
    }
    public int getQuantity() {
        return quantity.getQuantity();
    }
    public String getState() {return state.getState();}
}

