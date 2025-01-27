package com.springpractice.dao.repository.main;

import com.springpractice.dao.entities.main.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long>, BookDslRepository{

    @Query("SELECT BOOK FROM BookEntity BOOK JOIN FETCH BOOK.authorEntity")
    // @BatchSize(size = 30)
    List<BookEntity> findBookEntityAll();

}
