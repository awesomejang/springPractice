package com.springpractice.dao.repository.main;

import com.springpractice.dao.entities.main.ProductInventoryEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ProductInventoryRepository extends CrudRepository<ProductInventoryEntity, Long>, JpaSpecificationExecutor<ProductInventoryEntity> {
}
