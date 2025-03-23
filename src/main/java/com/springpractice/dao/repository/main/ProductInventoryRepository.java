package com.springpractice.dao.repository.main;

import com.springpractice.dao.entities.main.ProductInventoryEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductInventoryRepository extends CrudRepository<ProductInventoryEntity, Long>, JpaSpecificationExecutor<ProductInventoryEntity> {

    // TODO
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductInventoryEntity p WHERE p.idx = :productId")
    Optional<ProductInventoryEntity> findByIdWithPessimisticLock(@Param("productId") Long productId);
}
