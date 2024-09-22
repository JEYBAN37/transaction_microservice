package com.example.transaction.domain.model.entity.supplyvalidates;

import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class SupplyDate {
    LocalDateTime date;

    private SupplyDate(LocalDateTime  date) {
        this.date = date;
    }

    public static SupplyDate of() {
        return new SupplyDate(nowDate());
    }

    private static LocalDateTime  nowDate() {
        return LocalDateTime.now();
    }
}
