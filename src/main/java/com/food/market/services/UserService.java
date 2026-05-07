package com.food.market.services;


import java.util.List;

import com.food.market.dtos.FoodDTO;
import com.food.market.dtos.UserDTO;
import com.food.market.dtos.UserCreateDTO;

public interface UserService {
    UserDTO create(UserCreateDTO dto);
    List<UserDTO> findAll();
    UserDTO findByEmail(String email);
    UserDTO findById(Long id);
    UserDTO addFoodToUser(Long userId, FoodDTO foodDTO);
    void deleteById(Long id);
}
