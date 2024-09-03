package com.springpractice.dao.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springpractice.dao.entites.QUserAuthEntity;
import com.springpractice.dao.entites.UserAuthEntity;
import com.springpractice.dao.enums.AuthStatusEnum;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.springpractice.dao.entites.QUserAuthEntity.userAuthEntity;

@Repository
public class CustomUserAuthRepositoryImpl implements CustomUserAuthRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public CustomUserAuthRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<UserAuthEntity> findActiveUserAuth(String clientId, String clientSecret) {
        return Optional.ofNullable(
            jpaQueryFactory.selectFrom(userAuthEntity)
            .where(
                eqClientId(clientId),
                eqClientSecret(clientSecret),
                eqAuthStatus(AuthStatusEnum.ACTIVE),
                userAuthEntity.expirationDate.goe(LocalDateTime.now())
            ).fetchFirst()
        );
    }

    private BooleanExpression eqClientId(String clientId) {
        return clientId != null ? userAuthEntity.clientId.eq(clientId) : null;
    }

    private BooleanExpression eqClientSecret(String clientSecret) {
        return clientSecret != null ? userAuthEntity.clientSecret.eq(clientSecret) : null;
    }

    private BooleanExpression eqAuthStatus(AuthStatusEnum authStatusEnum) {
        return authStatusEnum != null ? userAuthEntity.authStatus.eq(authStatusEnum) : null;
    }

//    private BooleanExpression
}
