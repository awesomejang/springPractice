package com.springpractice.dao.repository.main;

import com.springpractice.dao.dto.BookWithPaginationResponseDTO;
import com.springpractice.dao.entities.main.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookDslRepository {

    Page<BookWithPaginationResponseDTO> findBookWithPagination(Pageable pageable);
}
