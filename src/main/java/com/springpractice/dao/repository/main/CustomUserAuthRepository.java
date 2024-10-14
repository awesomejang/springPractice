package com.springpractice.dao.repository.main;

import com.springpractice.dao.entities.main.UserAuthEntity;

import java.util.Optional;

public interface CustomUserAuthRepository {
    Optional<UserAuthEntity> findActiveUserAuth(String clientId, String clientSecret);
}
