package com.springpractice.dao.repositories;

import com.springpractice.dao.entities.main.UserMenuMappingEntity;
import com.springpractice.dao.repository.main.UserMenuMappingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
public class UserMenuMappingRepositoryTest {

    private final UserMenuMappingRepository userMenuMappingRepository;

    @Autowired
    public UserMenuMappingRepositoryTest(UserMenuMappingRepository userMenuMappingRepository) {
        this.userMenuMappingRepository = userMenuMappingRepository;
    }

    @Test
    void testFindMenuMappingByUserAuthEntity() {
        List<UserMenuMappingEntity> menuMappingByUserAuthEntityId = userMenuMappingRepository.findMenuMappingByUserAuthEntity_Id(33L);

        Assertions.assertFalse(menuMappingByUserAuthEntityId.isEmpty());

        assertThat(menuMappingByUserAuthEntityId).allMatch(Objects::nonNull);

        assertThat(menuMappingByUserAuthEntityId).allSatisfy(entity -> Assertions.assertEquals(33L, entity.getUserAuthEntity().getId()));
    }

    @Test
    void testFindMenuMappingByUserAuthEntity_Id() {
        List<Long> menuMappingByUserAuthEntity = userMenuMappingRepository.getMenuIdByMappingId(33L);

        Assertions.assertFalse(menuMappingByUserAuthEntity.isEmpty());

//        Assertions.assertFalse(menuMappingByUserAuthEntity.isEmpty());
//
//        assertThat(menuMappingByUserAuthEntity).allMatch(Objects::nonNull);
//
//        assertThat(menuMappingByUserAuthEntity).allSatisfy(entity -> Assertions.assertEquals(33L, entity.getUserAuthEntity().getId()));
    }
}
