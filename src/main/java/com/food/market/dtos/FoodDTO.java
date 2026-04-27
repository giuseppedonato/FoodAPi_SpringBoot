package com.food.market.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FoodDTO {
    private String uuid;

    private String name;

    private String category;

    private Integer calories;

    private BigDecimal price;

    private Boolean available;

    private LocalDate date;

    private LocalDateTime createdAt;
}
