package com.food.market.services;

import java.util.List;
import java.util.Optional;

import com.food.market.dtos.FoodDTO;

public interface FoodService {
    FoodDTO findByUuid(String uuid); 
    FoodDTO create(FoodDTO foodDTO);
    List<FoodDTO> findAll();
    Optional<FoodDTO> update(String uuid, FoodDTO foodDTO);
    Boolean delete(String uuid);
}
