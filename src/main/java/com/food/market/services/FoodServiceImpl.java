package com.food.market.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food.market.dtos.FoodDTO;
import com.food.market.models.Food;
import com.food.market.repositories.FoodRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FoodServiceImpl implements FoodService{
    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public FoodDTO findByUuid(String uuid) {
        Food food = foodRepository.findByUuid(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Non trovato"));

        return mapToDTO(food);
    }

    @Override
    public FoodDTO create(FoodDTO foodDTO) {
        Food food = new Food();
        maptoEntity(foodDTO, food);
        //Forzatura uuid
        food.setUuid(java.util.UUID.randomUUID().toString());

        Food saved = foodRepository.save(food);

        return mapToDTO(saved);
    }

    @Override
    public List<FoodDTO> findAll() {
        return foodRepository.findAll().stream()
            .map(this::mapToDTO).toList();
    }

    @Override
    public Optional<FoodDTO> update(String uuid, FoodDTO foodDTO) {
        Optional<Food> foodOpt = foodRepository.findByUuid(uuid);

        if(foodOpt.isEmpty()) return Optional.empty(); //Gestiso l'errore nel controller

        Food food = foodOpt.get();
        maptoEntity(foodDTO, food); //Entity Managed

        return Optional.of(mapToDTO(food));

    }

    @Override
    public Boolean delete(String uuid) {
        Optional<Food> food = foodRepository.findByUuid(uuid);

        if(food.isEmpty()) return false;

        foodRepository.delete(food.get());
        return true;
    }

    //MAPPING

    public FoodDTO mapToDTO(Food food) {
        FoodDTO dto = new FoodDTO();
        dto.setUuid(food.getUuid());
        dto.setName(food.getFoodName());
        dto.setCategory(food.getCategory());
        dto.setCalories(food.getCalories());
        dto.setPrice(food.getPrice());
        dto.setAvailable(food.getAvailable());
        dto.setDate(food.getDate());
        dto.setCreatedAt(food.getCreatedAt());

        return dto;
    }

    public void maptoEntity(FoodDTO dto, Food food) {
        food.setFoodName(dto.getName());
        food.setCategory(dto.getCategory());
        food.setCalories(dto.getCalories());
        food.setPrice(dto.getPrice());
        food.setAvailable(dto.getAvailable());
        food.setDate(dto.getDate());

        //Non serve il return perchè passo il riferimento
    }

}
