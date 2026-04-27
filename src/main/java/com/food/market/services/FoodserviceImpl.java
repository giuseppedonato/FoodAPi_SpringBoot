package com.food.market.services;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food.market.dtos.FoodDTO;
import com.food.market.models.Food;
import com.food.market.repositories.FoodRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FoodserviceImpl implements FoodService{
    private final FoodRepository foodRepository;

    public FoodserviceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public FoodDTO findByUuid(String uuid) {
        Food food = foodRepository.findByUuid(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Non trovato"));

        return mapToDTO(food);
    }

    @Override
    public FoodDTO create(FoodDTO foodDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public List<FoodDTO> findAll() {
        return foodRepository.findAll().stream()
            .map(this::mapToDTO).toList();
    }

    @Override
    public FoodDTO update(String uuid, FoodDTO foodDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
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

    public Food maptoEntity(FoodDTO dto, Food food) {
        food.setFoodName(dto.getName());
        food.setCategory(dto.getCategory());
        food.setCalories(dto.getCalories());
        food.setPrice(dto.getPrice());
        food.setAvailable(dto.getAvailable());
        food.setDate(dto.getDate());

        return food;
    }

}
