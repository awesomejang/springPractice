package com.springpractice.api.book;

import com.springpractice.api.book.service.BookService;
import com.springpractice.dao.dto.BookWithPaginationResponseDTO;
import com.springpractice.dao.entities.main.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("")
    public Page<BookWithPaginationResponseDTO> getBooksWithPagination(
            Long bookId,
            @PageableDefault(page = 0, size = 20) Pageable pageable
    ) {
        return bookService.getBookWithPagination(pageable, bookId);
    }
}
