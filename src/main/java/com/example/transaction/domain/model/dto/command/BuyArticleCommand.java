package com.example.transaction.domain.model.dto.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyArticleCommand {
    private Long idarticle;
    private int quantity;
}
