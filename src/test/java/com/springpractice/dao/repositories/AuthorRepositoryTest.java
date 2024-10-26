package com.springpractice.dao.repositories;

import com.springpractice.dao.entities.main.AuthorEntity;
import com.springpractice.dao.entities.main.BookEntity;
import com.springpractice.dao.repository.main.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class AuthorRepositoryTest {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    void selectByIdTest() {
        List<AuthorEntity> author = authorRepository.findByNameContaining("Author");
        for (AuthorEntity authorEntity : author) {
            for (BookEntity book : authorEntity.getBooks()) {
                System.out.println(book.getTitle());
            }
        }
    }
}
