package com.springpractice.api.inventory.service;

import com.springpractice.api.inventory.dto.ProductInventoryResDto;
import com.springpractice.dao.entities.main.ProductInventoryEntity;
import com.springpractice.dao.repository.main.ProductInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductInventoryTranService {

    private final ProductInventoryRepository productInventoryRepository;

    @Transactional
    public ProductInventoryResDto saveProductWithDecreasedQuantity(final Long productId, final int amount) throws InterruptedException {

        ProductInventoryEntity product = productInventoryRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.decreaseQuantity(amount);

        Thread.sleep(5000);

        return productInventoryRepository.save(product).toResDto();
    }
}
