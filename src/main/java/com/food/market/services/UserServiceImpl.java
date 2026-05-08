package com.food.market.services;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.food.market.dtos.FoodDTO;
import com.food.market.dtos.UserCreateDTO;
import com.food.market.dtos.UserDTO;
import com.food.market.models.Food;
import com.food.market.models.User;
import com.food.market.repositories.FoodRepository;
import com.food.market.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final FoodServiceImpl foodService;

    public UserServiceImpl(UserRepository userRepository, FoodRepository foodRepository, FoodServiceImpl foodService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.foodRepository = foodRepository;
        this.foodService = foodService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDTO create(UserCreateDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepository.save(user);

        if (dto.getFoods() != null) {
            dto.getFoods().forEach(foodDTO -> {
                Food food = new Food();
                foodService.maptoEntity(foodDTO, food);
                food.setUuid(java.util.UUID.randomUUID().toString());
                food.setUser(saved);

                Food savedFood = foodRepository.save(food);
                saved.getFoods().add(savedFood);
            });
        }

        return mapToDto(saved);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        return mapToDto(user);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return mapToDto(user);
    }

    @Override
    @Transactional
    public UserDTO addFoodToUser(Long userId, FoodDTO foodDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        Food food = new Food();
        foodService.maptoEntity(foodDTO, food);
        food.setUuid(java.util.UUID.randomUUID().toString());
        food.setUser(user);

        Food savedFood = foodRepository.save(food);
        user.getFoods().add(savedFood);

        return mapToDto(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente da eliminare non trovato"));

        userRepository.deleteById(id);
    }

    //MAPPING

    public UserDTO mapToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFoods(user.getFoods().stream()
                        .map(foodService::mapToDTO)
                        .toList());

        return dto;
    }

    public void mapToEntity(UserDTO dto, User user) {
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
    }

}
