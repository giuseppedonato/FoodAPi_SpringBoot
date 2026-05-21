package com.food.market.controllers;

import com.food.market.security.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.market.dtos.LoginRequestDTO;
import com.food.market.dtos.LoginResponseDTO;
import com.food.market.dtos.RefreshTokenRequestDTO;
import com.food.market.models.RefreshToken;
import com.food.market.models.User;
import com.food.market.security.JwtService;
import com.food.market.services.CustomUserDetailsService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService,
            JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String accessToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(accessToken);
        response.setRefreshToken(refreshToken.getToken());

        return response;
    }

    @PostMapping("/refresh")
    public LoginResponseDTO refresh(@RequestBody RefreshTokenRequestDTO requestDTO) {
        RefreshToken refreshToken = refreshTokenService.validaRefreshToken(requestDTO.getRefreshToken());

        User user = refreshToken.getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        String newAccessToken = jwtService.generateToken(userDetails);

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken(newAccessToken);
        responseDTO.setRefreshToken(refreshToken.getToken());

        return responseDTO;
    }

    @PostMapping("/logout")
    public void logout(@RequestBody RefreshTokenRequestDTO requestDTO) {
        refreshTokenService.revokeRefreshToken(requestDTO.getRefreshToken());
    }


    
}
