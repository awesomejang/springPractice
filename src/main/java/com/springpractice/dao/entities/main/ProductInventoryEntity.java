package com.springpractice.dao.entities.main;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_inventory")
@Getter
@Entity
public class ProductInventoryEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "product_code", columnDefinition = "CHAR(10)", nullable = false)
    private String productCode;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    // 낙관적 락(Optimistic Lock)을 위한 버전 필드
    @Version
    private Long version;

    public ProductInventoryEntity(String productCode, String productName, Integer quantity, BigDecimal unitPrice) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void decreaseQuantity(int amount) {
        if (this.quantity <= 0) {
            throw new IllegalStateException("재고가 부족합니다.");
        }

        if (this.quantity < amount) {
            throw new IllegalStateException("재고가 부족합니다.");
        }

        this.quantity -= amount;
    }
}
