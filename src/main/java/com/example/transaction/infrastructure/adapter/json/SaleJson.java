package com.example.transaction.infrastructure.adapter.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SaleJson {
    private Long id;
    private int quantity;
    private String name;
    private BrandJson brand;
    private BigDecimal price;
    private String description;
    private List<CategoriesJson> articleCategories;
}
