package com.example.transaction.domain.model.dto;

import com.example.transaction.domain.model.entity.salesvalidates.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
public class SaleDto {
    private Long idArticle;
    private int quantity;
    private State state;
    private BigDecimal price;
    private LocalDateTime date;
}
