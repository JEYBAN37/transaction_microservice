package com.example.transaction.infrastructure.adapter.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class SupplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idArticle;
    private int quantity;
    private String state;
    private BigDecimal price;
    private LocalDateTime date;
}
