package com.springpractice.dao.repositories;

import com.springpractice.dao.entities.main.AuthorEntity;
import com.springpractice.dao.entities.main.BookEntity;
import com.springpractice.dao.repository.main.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class BookRepositoryTest {

    private final BookRepository bookRepository;

    @Autowired // TODO
    public BookRepositoryTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void selectTest() {
        List<BookEntity> all = bookRepository.findBookEntityAll();
        for (BookEntity bookEntity : all) {
            AuthorEntity authorEntity = bookEntity.getAuthorEntity();
            System.out.println(authorEntity.getName());
        }
    }
}
