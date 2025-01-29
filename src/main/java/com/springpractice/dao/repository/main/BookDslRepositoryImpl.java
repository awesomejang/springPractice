package com.springpractice.dao.repository.main;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springpractice.dao.dto.BookWithPaginationResponseDTO;
import com.springpractice.dao.dto.QBookWithPaginationResponseDTO;
import com.springpractice.dao.entities.main.QBookEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.function.LongSupplier;

@Repository
public class BookDslRepositoryImpl implements BookDslRepository{

    private final JPAQueryFactory queryFactory;

    public BookDslRepositoryImpl(@Qualifier(value = "mainJpaQueryFactory") JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<BookWithPaginationResponseDTO> findBookWithPagination(Pageable pageable, Long bookId) {
        QBookEntity bookEntity = QBookEntity.bookEntity;

        List<BookWithPaginationResponseDTO> books = queryFactory.select(new QBookWithPaginationResponseDTO(bookEntity.id, bookEntity.title, bookEntity.authorEntity.id))
                .from(bookEntity)
                .where(
                        EqBookId(bookId)
                )
                .offset(pageable.getOffset()) // 시작 지점
                .limit(pageable.getPageSize()) // 한 페이지 크기
                .fetch();

        // IDE warning 때문에 null 처리 했으나 결과 없을때 0L 리턴
        Long totalCount = Objects.requireNonNullElse(
                queryFactory.select(bookEntity.count())
                        .from(bookEntity)
                        .where(
                                EqBookId(bookId)
                        )
                        .fetchOne(), 0L);

//        LongSupplier totalSupplier = () -> queryFactory.select(bookEntity.count())
//                .from(bookEntity)
//                .where(
//                        EqBookId(bookId)
//                )
//                .fetchOne();


        // 카운터 쿼리가 불필요할 시 수행하지 않는다. -> 카운터 쿼리의 비용이 클때 이점
//        return PageableExecutionUtils.getPage(books, pageable, totalSupplier);
        return new PageImpl<>(books, pageable, totalCount);
    }

    private BooleanExpression EqBookId(Long bookId) {
        QBookEntity bookEntity = QBookEntity.bookEntity;
        if (bookId == null) {
            return null;
        }
        return bookId == 0 ? null : bookEntity.id.eq(bookId);
    }
}
