package com.food.market.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.market.dtos.FoodDTO;
import com.food.market.services.FoodService;

@RestController
@RequestMapping("/api/foods")
public class FoodController{

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/{uuid}")
    public FoodDTO findByUuid(@PathVariable String uuid) {
        return foodService.findByUuid(uuid);
    }
    
    @GetMapping
    public List<FoodDTO> findAll() {
        return foodService.findAll();
    }

}
