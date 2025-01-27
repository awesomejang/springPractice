package com.springpractice.api.book.service;

import com.springpractice.dao.dto.BookWithPaginationResponseDTO;
import com.springpractice.dao.entities.main.BookEntity;
import com.springpractice.dao.repository.main.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Page<BookWithPaginationResponseDTO> getBookWithPagination(Pageable pageable) {
        return bookRepository.findBookWithPagination(pageable);
    }
}
