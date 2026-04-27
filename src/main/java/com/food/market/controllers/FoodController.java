package com.food.market.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodDTO create(@RequestBody FoodDTO dto) {
        return foodService.create(dto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String uuid) {
        boolean deleted = foodService.delete(uuid);

        if(!deleted) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{uuid}")
    public FoodDTO update(@PathVariable String uuid, @RequestBody FoodDTO dto) {
        return foodService.update(uuid, dto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
