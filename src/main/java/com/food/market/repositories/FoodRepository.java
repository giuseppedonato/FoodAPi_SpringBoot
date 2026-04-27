package com.food.market.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.market.models.Food;



public interface FoodRepository extends JpaRepository<Food, Long>{
    Optional<Food> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    Boolean existsByUuid(String uuid); 



}
