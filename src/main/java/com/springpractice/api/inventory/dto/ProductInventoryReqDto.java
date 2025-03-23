package com.springpractice.api.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ProductInventoryReqDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductQuantityReqDto {
        private int quantity;
    }

}
