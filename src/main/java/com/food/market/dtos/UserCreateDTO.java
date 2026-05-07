package com.food.market.dtos;

import java.util.List;

import lombok.Data;

@Data
public class UserCreateDTO {
    private String username;
    private String email;
    private String password;
    private List<FoodDTO> foods;
}
