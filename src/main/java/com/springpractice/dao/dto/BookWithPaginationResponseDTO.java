package com.springpractice.dao.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class BookWithPaginationResponseDTO {

    private Long id;
    private String title;
    private Long authorId;

    @QueryProjection
    public BookWithPaginationResponseDTO(Long id, String title, Long authorId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
    }
}
