package com.springpractice.dao.repository;

import com.springpractice.dao.entites.UserAuthEntity;
import com.springpractice.dao.entites.UserMenuMappingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserMenuMappingRepository extends CrudRepository<UserMenuMappingEntity, Long>, CustomUserMenuMappingRepository {

    List<UserMenuMappingEntity> findMenuMappingByUserAuthEntity(UserAuthEntity userAuthEntity);

    List<UserMenuMappingEntity> findMenuMappingByUserAuthEntity_Id(Long userAuthId);
}
