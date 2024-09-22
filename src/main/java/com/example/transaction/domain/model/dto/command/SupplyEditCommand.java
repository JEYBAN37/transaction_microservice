package com.example.transaction.domain.model.dto.command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SupplyEditCommand {
    private Long idArticle;
    private int quantity;
    private String state;
}