package com.food.market.dtos;

import lombok.Data;

@Data //serve per ricevere il refresh token dal frontend
public class RefreshTokenRequestDTO {
    private String refreshToken;
}
