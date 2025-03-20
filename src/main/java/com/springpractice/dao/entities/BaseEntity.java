package com.springpractice.dao.entities;

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
    @Column(name = "created_by", updatable = false, columnDefinition = "VARCHAR(500)")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by", columnDefinition = "VARCHAR(500)")
    private String updatedBy;
}
