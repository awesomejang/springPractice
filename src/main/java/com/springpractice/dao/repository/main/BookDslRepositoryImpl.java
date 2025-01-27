package com.springpractice.dao.repository.main;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springpractice.dao.dto.BookWithPaginationResponseDTO;
import com.springpractice.dao.dto.QBookWithPaginationResponseDTO;
import com.springpractice.dao.entities.main.QBookEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDslRepositoryImpl implements BookDslRepository{

    private final JPAQueryFactory queryFactory;

    public BookDslRepositoryImpl(@Qualifier(value = "mainJpaQueryFactory") JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<BookWithPaginationResponseDTO> findBookWithPagination(Pageable pageable) {
        QBookEntity bookEntity = QBookEntity.bookEntity;

        List<BookWithPaginationResponseDTO> books = queryFactory.select(new QBookWithPaginationResponseDTO(bookEntity.id, bookEntity.title, bookEntity.authorEntity.id))
                .from(bookEntity)
                .offset(pageable.getOffset()) // 시작 지점
                .limit(pageable.getPageSize()) // 한 페이지 크기
                .fetch();

        Long totalCount = queryFactory.select(bookEntity.count())
                .from(bookEntity)
                .fetchOne();

        // TODO: PageUtils???
        return new PageImpl<>(books, pageable, totalCount);
    }
}
