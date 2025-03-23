package com.springpractice.api.inventory.controller;

import com.springpractice.api.inventory.dto.ProductInventoryReqDto;
import com.springpractice.api.inventory.dto.ProductInventoryResDto;
import com.springpractice.api.inventory.service.ProductInventoryService;
import com.springpractice.dao.repository.main.ProductInventoryRepository;
import com.springpractice.dtos.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class ProductInventoryController {

    private final ProductInventoryService productInventoryService;

    @GetMapping("/{idx}")
    public ResponseEntity<CommonResponseDto<ProductInventoryResDto>> getInventory(@PathVariable Long idx) {
        // productInventoryService.getProductInventory(idx)
        return ResponseEntity.ok(CommonResponseDto.success(productInventoryService.getProductInventory(idx).toResDto()));
    }

    @PatchMapping("/{idx}/decrease")
    public ResponseEntity<CommonResponseDto<ProductInventoryResDto>> decreaseQuantity(@PathVariable Long idx, @RequestBody ProductInventoryReqDto.ProductQuantityReqDto reqeust) {
        return ResponseEntity.ok(CommonResponseDto.success(productInventoryService.decreaseQuantity(idx, reqeust.getQuantity())));
    }

    @PatchMapping("/{idx}/decrease/trans")
    public ResponseEntity<CommonResponseDto<ProductInventoryResDto>> decreaseTransQuantity(@PathVariable Long idx, @RequestBody ProductInventoryReqDto.ProductQuantityReqDto reqeust) {
        return ResponseEntity.ok(CommonResponseDto.success(productInventoryService.decreaseQuantity(idx, reqeust.getQuantity())));
    }
}
