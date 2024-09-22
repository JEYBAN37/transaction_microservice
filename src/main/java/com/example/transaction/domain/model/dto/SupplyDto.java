package com.example.transaction.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SupplyDto {
    private Long id;
    private Long idArticle;
    private int quantity;
    private String state;
    private BigDecimal price;
    private String date;
}
