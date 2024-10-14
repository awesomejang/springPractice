package com.springpractice.dao.repository.main;

import com.springpractice.dao.entities.main.UserAuthEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAuthRepository extends CrudRepository<UserAuthEntity, Long>, CustomUserAuthRepository {

    Optional<UserAuthEntity> findUserByClientIdAndClientSecret(String clientId, String clientSecret);
}
