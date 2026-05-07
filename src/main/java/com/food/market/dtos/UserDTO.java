package com.food.market.dtos;

import java.util.List;


import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    List<FoodDTO> foods;
}
