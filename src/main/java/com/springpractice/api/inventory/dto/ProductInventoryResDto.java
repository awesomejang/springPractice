package com.springpractice.api.inventory.dto;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Builder
@Getter
public class ProductInventoryResDto {
    private Long id;
    private String productCode;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
}
