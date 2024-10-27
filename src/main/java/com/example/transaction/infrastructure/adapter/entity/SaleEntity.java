package com.example.transaction.infrastructure.adapter.entity;

import com.example.transaction.domain.model.entity.salesvalidates.State;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sale")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idArticle;
    private Long idUser;
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    private BigDecimal price;
    private LocalDateTime date;
}
