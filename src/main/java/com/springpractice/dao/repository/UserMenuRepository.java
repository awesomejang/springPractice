package com.springpractice.dao.repository;

import com.springpractice.dao.entites.UserMenuEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserMenuRepository extends CrudRepository<UserMenuEntity, Long> {
}
