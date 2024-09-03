package com.springpractice.dao.repository;

import com.springpractice.dao.entites.UserAuthEntity;

import java.util.List;
import java.util.Optional;

public interface CustomUserAuthRepository {
    Optional<UserAuthEntity> findActiveUserAuth(String clientId, String clientSecret);
}
