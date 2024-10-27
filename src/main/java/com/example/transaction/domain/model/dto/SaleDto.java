package com.example.transaction.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
public class SaleDto {
    private Integer idArticle;
    private int quantity;
    private String state;
    private BigDecimal price;
    private LocalDateTime date;
}
