package com.springpractice.dao.repository.main;

import com.springpractice.dao.entities.main.UserAuthEntity;
import com.springpractice.dao.entities.main.UserMenuMappingEntity;

import java.util.List;

public interface UserMenuMappingCustomRepository {

    List<UserMenuMappingEntity> getUserMenuMappingByListByUserId(UserAuthEntity userAuthEntity);

    List<Long> getMenuIdByMappingId(Long mappingId);
}
