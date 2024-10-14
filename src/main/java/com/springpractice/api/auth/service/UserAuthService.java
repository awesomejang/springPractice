package com.springpractice.api.auth.service;

import com.springpractice.dao.entities.main.UserAuthEntity;
import com.springpractice.dao.repository.main.UserAuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthService.class);
    private final UserAuthRepository userAuthRepository;

    public UserAuthService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    public boolean checkValidUser(String clientId, String clientSecret) {
        Optional<UserAuthEntity> userAuthEntity = userAuthRepository.findActiveUserAuth(clientId, clientSecret);

        return userAuthEntity.isPresent();
//        if (userAuthEntity.isEmpty()) {
////            logger.warn("Invalid client id or client secret target user: {}", clientId);
//            return false;
//        }
//        return true;
    }
}
