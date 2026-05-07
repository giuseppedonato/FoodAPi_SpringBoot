package com.food.market.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.market.dtos.FoodDTO;
import com.food.market.dtos.UserCreateDTO;
import com.food.market.dtos.UserDTO;
import com.food.market.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
private final UserService userService;

public UserController(UserService userService) {
    this.userService = userService;
}

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public UserDTO create(@RequestBody UserCreateDTO dto) {
    return userService.create(dto);
}

@GetMapping
public List<UserDTO> findAll() {
    return userService.findAll();
}

@GetMapping("/email")
public UserDTO findByEmail(@RequestParam String email) {
    return userService.findByEmail(email);
}

@GetMapping("/{id}") 
public UserDTO findById(@PathVariable Long id) {
    return userService.findById(id);
}

@PostMapping("/{id}/foods")
public UserDTO addFoodToUser(@PathVariable Long id, @RequestBody FoodDTO foodDTO) {
    return userService.addFoodToUser(id, foodDTO);
}

@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteById(@PathVariable Long id) {
    userService.deleteById(id);
}
}
