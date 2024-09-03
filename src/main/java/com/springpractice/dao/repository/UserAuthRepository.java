package com.springpractice.dao.repository;

import com.springpractice.dao.entites.UserAuthEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAuthRepository extends CrudRepository<UserAuthEntity, Long>, CustomUserAuthRepository {

    Optional<UserAuthEntity> findUserByClientIdAndClientSecret(String clientId, String clientSecret);



}
