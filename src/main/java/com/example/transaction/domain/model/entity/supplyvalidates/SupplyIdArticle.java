package com.example.transaction.domain.model.entity.supplyvalidates;

import com.example.transaction.domain.model.exception.SupplyException;
import lombok.Getter;

import static com.example.transaction.domain.model.constant.SupplyConstant.*;

@Getter
public class SupplyIdArticle {

    Integer idArticle;
    private SupplyIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }
    public static SupplyIdArticle of (Integer idArticle){
        toValidQuantity(idArticle);
        return new SupplyIdArticle(idArticle);
    }
    private static void toValidQuantity(Integer idArticle) {
        if(idArticle == null || String.valueOf(idArticle).isEmpty())
            throw new SupplyException(ARTICLE_MANDATORY);
        if (idArticle < ZERO_CONSTANT) {
            throw new SupplyException(ARTICLE_MESSAGE_MIN_ERROR);
        }
    }
}
