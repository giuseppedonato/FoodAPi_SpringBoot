package com.food.market.services;

import java.util.List;

import com.food.market.dtos.FoodDTO;

public interface FoodService {
    FoodDTO findByUuid(String uuid); 
    FoodDTO create(FoodDTO foodDTO);
    List<FoodDTO> findAll();
    FoodDTO update(String uuid, FoodDTO foodDTO);
    void delete(String uuid);
}
