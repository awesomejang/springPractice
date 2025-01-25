package com.springpractice.dao.repository.main;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springpractice.dao.entities.main.QUserMenuMappingEntity;
import com.springpractice.dao.entities.main.UserAuthEntity;
import com.springpractice.dao.entities.main.UserMenuMappingEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMenuMappingCustomRepositoryImpl implements UserMenuMappingCustomRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public UserMenuMappingCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserMenuMappingEntity> getUserMenuMappingByListByUserId(UserAuthEntity userAuthEntity) {
        QUserMenuMappingEntity userMenuMappingEntity = QUserMenuMappingEntity.userMenuMappingEntity;

        return queryFactory.select(userMenuMappingEntity)
                        .from(userMenuMappingEntity)
                        .where(eqUserAuthId(userMenuMappingEntity, userAuthEntity.getId()))
                        .fetch();
    }

    @Override
    public List<Long> getMenuIdByMappingId(Long mappingId) {
        QUserMenuMappingEntity userMenuMappingEntity = QUserMenuMappingEntity.userMenuMappingEntity;

        return queryFactory.select(userMenuMappingEntity)
                .from(userMenuMappingEntity) // TODO TUPLE
                .where(userMenuMappingEntity.userAuthEntity.id.eq(mappingId))
                .fetch()
                .stream().map(entity -> entity.getUserMenuEntity().getId()).toList();
    }

    private BooleanExpression eqUserAuthId(QUserMenuMappingEntity userMenuMappingEntity, Long id) {
        return userMenuMappingEntity.userAuthEntity.id.eq(id);
    }

    private BooleanExpression eqMappingId(QUserMenuMappingEntity userMenuMappingEntity, Long id) {
        return userMenuMappingEntity.id.eq(id);
    }
}
