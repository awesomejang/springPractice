package com.springpractice.api.inventory.service;

import com.springpractice.api.inventory.dto.ProductInventoryResDto;
import com.springpractice.common.annotation.OptimisticLockRetry;
import com.springpractice.dao.entities.main.ProductInventoryEntity;
import com.springpractice.dao.repository.main.ProductInventoryRepository;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductInventoryService {

    private final ProductInventoryRepository productInventoryRepository;
    private final ProductInventoryTranService productInventoryTranService;

    /**
     * 낙관적 락 catch ObjectOptimisticLockingFailureException 은
     * @Transactional로 잡을랬더니 트랜잭션 종료시점에 version비교해서 version이 변경했는지 확인하기 때문에
     * 같은 레이어에서 잡을수가 없어서 트랜잭션 분리가 필요하다.
      */
    @OptimisticLockRetry(maxRetries = 3, initialDelay = 1000, backoffFactor = 3.0)
    public ProductInventoryResDto decreaseQuantity(Long productId, int amount) {


        try {
            return productInventoryTranService.saveProductWithDecreasedQuantity(productId, amount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        while (retryCount < maxRetries) {
//            try {
//                return productInventoryTranService.saveProductWithDecreasedQuantity(productId, amount);
//            } catch (ObjectOptimisticLockingFailureException e) {
//                retryCount++;
//                if (retryCount >= maxRetries) {
//                    ProductInventoryEntity latestProduct = productInventoryRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
//                    throw new IllegalArgumentException("낙관적 락 발생! 제품의 남은 제품수 : " + latestProduct.getQuantity());
//                }
//                try {
//                    log.info("[재고 주문 Retry count] : {}", retryCount);
//                    Thread.sleep(100 * (long)Math.pow(2, retryCount));
//                } catch (InterruptedException ex) {
//                    throw new RuntimeException(ex);
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                throw new RuntimeException(e);
//            }
//        }
//        throw new RuntimeException("구매에 실패했습니다.");

//        try {
//            return productInventoryTranService.saveProductWithDecreasedQuantity(productId, amount);
//        } catch (ObjectOptimisticLockingFailureException e) {
//            ProductInventoryEntity latestProduct = productInventoryRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
//            throw new IllegalArgumentException("낙관적 락 발생! 제품의 남은 제품수 : " + latestProduct.getQuantity());
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException(e);
//        }
    }

    @Transactional(readOnly = true)
    public ProductInventoryEntity getProductInventory(Long productId) {
        return productInventoryRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    // TODO Transactional 비관적 락은 트랜잭션 내에서만 동작하기 때문에 이 메서드를 호출하는 곳에 @Transactional 어노테이션이 필요합니다.
    @Transactional
    public ProductInventoryResDto decreaseQuantityWithPessimisticLock(Long productId, int amount) {
        ProductInventoryEntity product = productInventoryRepository.findByIdWithPessimisticLock(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        product.decreaseQuantity(amount);
        return productInventoryRepository.save(product).toResDto();
    }
}
