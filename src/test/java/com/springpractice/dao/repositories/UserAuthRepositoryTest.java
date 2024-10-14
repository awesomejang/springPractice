package com.springpractice.dao.repositories;

import com.springpractice.dao.entities.main.UserAuthEntity;
import com.springpractice.dao.entities.main.UserMenuEntity;
import com.springpractice.dao.entities.main.UserMenuMappingEntity;
import com.springpractice.dao.enums.AuthStatusEnum;
import com.springpractice.dao.enums.UserAuthGradeEnum;
import com.springpractice.dao.repository.main.UserAuthRepository;
import com.springpractice.dao.repository.main.UserMenuMappingRepository;
import com.springpractice.dao.repository.main.UserMenuRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;


@SpringBootTest
@Transactional
@Profile("local") // TODO: logger debug로 하면 출력 안됨
public class UserAuthRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthRepositoryTest.class);
    private final EntityManager em;
    private final UserAuthRepository userAuthRepository;
    private final UserMenuRepository userMenuRepository;
    private final UserMenuMappingRepository userMenuMappingRepository;

    @Autowired
    public UserAuthRepositoryTest(EntityManager entityManager,
                                  UserAuthRepository userAuthRepository,
                                  UserMenuRepository userMenuRepository,
                                  UserMenuMappingRepository userMenuMappingRepository) {
        this.em = entityManager;
        this.userAuthRepository = userAuthRepository;
        this.userMenuRepository = userMenuRepository;
        this.userMenuMappingRepository = userMenuMappingRepository;
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
        UserAuthEntity user = new UserAuthEntity.Builder()
                .clientId("jang")
                .clientSecret("1234")
                .userAuthGrade(UserAuthGradeEnum.SENIOR)
                .build();

        userAuthRepository.save(user);
        em.flush();
        em.clear();

        Optional<UserAuthEntity> byId = userAuthRepository.findById(user.getId());
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("UserAuthEntity is empty");
        } else {
            UserAuthEntity userAuthEntity = byId.get();
            Assertions.assertEquals(userAuthEntity.getUserAuthGrade().getGrade(), "senior");
        }
    }

    @Test
    void testSelectUserAuthMapping() {

        UserAuthEntity userAuthEntity = new UserAuthEntity.Builder()
                .clientId("testClient1")
                .clientSecret("testClientSecret1")
                .authStatus(AuthStatusEnum.INACTIVE)
                .expirationDate(LocalDateTime.now().plusDays(1))
                .build();
        userAuthRepository.save(userAuthEntity);

        UserMenuEntity userMenuEntity = new UserMenuEntity("testMenu", "test/a", "testUrl1");
        userMenuRepository.save(userMenuEntity);

        UserMenuMappingEntity userMenuMappingEntity1 = new UserMenuMappingEntity(userAuthEntity, userMenuEntity);
        userMenuMappingRepository.save(userMenuMappingEntity1);
        em.flush();
        em.clear();

        // when
        UserAuthEntity userAuthEntity1 = userAuthRepository.findById(userAuthEntity.getId()).orElseThrow();
        EntityManagerFactory entityManagerFactory = em.getEntityManagerFactory();
        PersistenceUnitUtil persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();


        if (!persistenceUnitUtil.isLoaded(userAuthEntity1.getUserMenuMappingEntities())) {
            Set<UserMenuMappingEntity> userMenuMappingEntities = userAuthEntity1.getUserMenuMappingEntities();
            Assertions.assertFalse(userMenuMappingEntities.isEmpty());
            org.assertj.core.api.Assertions.assertThat(userMenuMappingEntities.size()).isEqualTo(1);
        }else {
            throw new IllegalStateException("userMenuMappingEntities is not loaded");
        }
    }
}
