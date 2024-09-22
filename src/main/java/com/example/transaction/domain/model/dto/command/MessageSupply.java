package com.example.transaction.domain.model.dto.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MessageSupply {
    private Long id;
    private int quantity;
    private BigDecimal price;
}
