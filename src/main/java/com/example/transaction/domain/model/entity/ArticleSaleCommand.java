package com.example.transaction.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ArticleSaleCommand {
    private Long id;
    private int quantity;


}
