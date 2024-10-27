package com.example.transaction.domain.model.entity;

import com.example.transaction.domain.model.entity.salesvalidates.ArticlePrice;
import com.example.transaction.domain.model.entity.salesvalidates.ArticleQuantity;
import com.example.transaction.domain.model.entity.salesvalidates.State;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Sale {
    private Long id;
    private Long idArticle;
    private Long idUser;
    private ArticleQuantity quantity;
    private State state;
    private ArticlePrice price;
    private LocalDateTime date;

    public Sale (Long idArticle,Long idUser, int quantity, State state, BigDecimal price){
        this.idArticle = idArticle;
        this.idUser = idUser;
        this.quantity = ArticleQuantity.of(quantity);
        this.state = state;
        this.price = ArticlePrice.of(price);
        this.date = LocalDateTime.now();
    }

    public static Sale canceled (Long idArticle, Long idUser, int quantity, State state){
        return new Sale(idArticle, idUser, quantity, state, BigDecimal.ZERO);
    }

    public static Sale jsonSale (Long idArticle, int quantity, State state, BigDecimal price){
        return new Sale(idArticle,0L, quantity, state, price);
    }

    public BigDecimal getPrice (){return price.getPrice();}
    public int getQuantity (){return quantity.getQuantity();}

}
