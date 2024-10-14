package com.springpractice.dao.repository.main;

import com.springpractice.dao.entities.main.UserAuthEntity;
import com.springpractice.dao.entities.main.UserMenuMappingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserMenuMappingRepository extends CrudRepository<UserMenuMappingEntity, Long>, CustomUserMenuMappingRepository {

    List<UserMenuMappingEntity> findMenuMappingByUserAuthEntity(UserAuthEntity userAuthEntity);

    List<UserMenuMappingEntity> findMenuMappingByUserAuthEntity_Id(Long userAuthId);
}
