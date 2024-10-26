package com.springpractice.dao.repository.main;

import com.springpractice.dao.entities.main.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    @Query("SELECT author FROM AuthorEntity author JOIN FETCH author.books where author.name like %:name%")
    List<AuthorEntity> findByNameContaining(String name);

}
