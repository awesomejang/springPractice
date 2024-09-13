package com.springpractice.dao.repositories;

import com.springpractice.dao.entites.UserAuthEntity;
import com.springpractice.dao.enums.AuthStatusEnum;
import com.springpractice.dao.repository.UserAuthRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;


@SpringBootTest
@Transactional
public class UserAuthRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthRepositoryTest.class);
    private final UserAuthRepository userAuthRepository;
    private final EntityManager em;

    @Autowired
    public UserAuthRepositoryTest(UserAuthRepository userAuthRepository, EntityManager entityManager) {
        this.userAuthRepository = userAuthRepository;
        this.em = entityManager;
    }

    @Test
    public void insertUserAuthTest() {
        UserAuthEntity user = new UserAuthEntity.Builder()
                .clientId("jang")
                .clientSecret("1234")
                .authStatus(AuthStatusEnum.INACTIVE)
                .expirationDate(LocalDateTime.now())
                .build();
        userAuthRepository.save(user);

        em.flush();
        em.clear();

        Optional<UserAuthEntity> userAuthEntity = userAuthRepository.findById(user.getId());
        Assertions.assertTrue(userAuthEntity.isPresent());

        Assertions.assertEquals("jang", userAuthEntity.get().getClientId());
        Assertions.assertEquals("1234", userAuthEntity.get().getClientSecret());
    }

    @Test
    public void converterTest() {
//        new UserAuthEntity(
//                "jang",
//                "1234",
//                AuthStatusEnum.INACTIVE,
//                LocalDateTime.now()
//        )
    }
}
