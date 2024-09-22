package com.example.transaction.domain.model.dto.command;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SupplyCreateCommand {
    private Long id;
    private Long idArticle;
    private int quantity;
    private String state;
    private BigDecimal price;
    private String date;
}
