package com.springpractice.dao.repository.main;

import com.springpractice.dao.entites.main.UserMenuEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserMenuRepository extends CrudRepository<UserMenuEntity, Long> {
}
