package com.springpractice.dao.entites;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(name = "created_by", updatable = false, nullable = true, columnDefinition = "VARCHAR(500)")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", nullable = true, columnDefinition = "VARCHAR(500)")
    private String updatedBy;
}
