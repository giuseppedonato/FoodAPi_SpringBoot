package com.food.market.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.market.models.RefreshToken;
import com.food.market.models.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    Optional<RefreshToken> findByToken(String token); //cerca il refreshToken quando il client lo manda al backend
    void deleteByUser(User user);
}
