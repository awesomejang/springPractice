package com.springpractice.api.inventory.service;

import com.springpractice.dao.entities.main.ProductInventoryEntity;
import com.springpractice.dao.repository.main.ProductInventoryRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class ProductInventoryServiceTest {

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    private ProductInventoryService productInventoryService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private Long testProductId;

    @BeforeEach
    void setup() {
        ProductInventoryEntity product = new ProductInventoryEntity(
                "TEST" + System.currentTimeMillis(),
                "테스트 상품",
                100,
                new BigDecimal("1000.00"));

        testProductId = productInventoryRepository.save(product).getIdx();
    }

    @Test
    void testOptimisticLock() throws InterruptedException {
        // 동시 실행 쓰레드수
        int numberOfThreads = 10;

        // 각 쓰레드에서 감소시킬 재고 수량
        int decreaseAmount  = 10;

        // ExecutorService 생성
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        // 모든 쓰레드 동시 시작
        CountDownLatch latch = new CountDownLatch(1);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        // 비동기 작업 추적을 위한 Future 리스트
        List<Runnable> tasks = new ArrayList<>();

        // 각 쓰레드가 실행할 작업 정의
        for (int i = 0; i < numberOfThreads; i++) {
            tasks.add(() -> {
                try {
                    // 모든 쓰레드가 동시에 시작하도록 대기
                    latch.await();

                    // 재고 감소 시도
                    productInventoryService.decreaseQuantity(testProductId, decreaseAmount);

                    // 성공 카운트 증가
                    successCount.incrementAndGet();
                    System.out.println("재고 감소 성공: " + Thread.currentThread().getName());
                } catch (OptimisticLockException e) {
                    // 낙관적 락 예외 발생 시 실패 카운트 증가
                    failCount.incrementAndGet();
                    System.out.println("낙관적 락 예외 발생: " + Thread.currentThread().getName());
                } catch (Exception e) {
                    // 기타 예외 처리
                    System.out.println("예외 발생: " + e.getMessage());
                }
            });
        }

        // 모든 작업을 실행 큐에 제출
        for (Runnable task : tasks) {
            executorService.submit(task);
        }

        // 모든 쓰레드 동시에 시작하도록 신호
        latch.countDown();

        // 모든 쓰레드가 작업을 마칠 때까지 대기하기 위해 쓰레드 풀 종료
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }

        // 테스트 결과 검증
        System.out.println("성공한 트랜잭션: " + successCount.get());
        System.out.println("실패한 트랜잭션: " + failCount.get());

        // 최종 재고 확인
        ProductInventoryEntity finalProduct = productInventoryService.getProductInventory(testProductId);
        System.out.println("최종 재고: " + finalProduct.getQuantity());

        // 검증: 성공한 트랜잭션 수 + 실패한 트랜잭션 수 = 총 쓰레드 수
        assertEquals(numberOfThreads, successCount.get() + failCount.get());

        // 검증: 최종 재고 = 초기 재고(100) - (성공한 트랜잭션 수 * 감소량)
        assertEquals(100 - (successCount.get() * decreaseAmount), finalProduct.getQuantity());
    }

    @Test
    void testPessimisticLock() throws Exception {
        // 테스트 데이터 준비
        ProductInventoryEntity product = new ProductInventoryEntity(
                "TEST" + System.currentTimeMillis(), // 고유한 코드 사용
                "Test Product", 100, new BigDecimal("10.00"));
        product = productInventoryRepository.save(product);
        final Long productId = product.getIdx();

        // 트랜잭션 매니저 사용 (비관적 락 테스트에 중요)
        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

        // 두 개의 스레드로 동시에 요청
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch startLatch = new CountDownLatch(1); // 동시 시작을 위한 래치
        CountDownLatch completeLatch = new CountDownLatch(2);
        AtomicReference<Exception> thread1Exception = new AtomicReference<>();
        AtomicReference<Exception> thread2Exception = new AtomicReference<>();

        // 첫 번째 스레드
        Future<?> future1 = executor.submit(() -> {
            try {
                startLatch.await(); // 두 스레드가 거의 동시에 시작하도록 대기
                txTemplate.execute(status -> {
                    System.out.println("Thread 1 starting: " + System.currentTimeMillis());
                    ProductInventoryEntity entity = productInventoryRepository.findByIdWithPessimisticLock(productId)
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    entity.decreaseQuantity(30);
                    productInventoryRepository.save(entity);
                    System.out.println("Thread 1 completed: " + System.currentTimeMillis());
                    return null;
                });
            } catch (Exception e) {
                thread1Exception.set(e);
                e.printStackTrace();
            } finally {
                completeLatch.countDown();
            }
        });

        // 두 번째 스레드
        Future<?> future2 = executor.submit(() -> {
            try {
                startLatch.await(); // 두 스레드가 거의 동시에 시작하도록 대기
                txTemplate.execute(status -> {
                    System.out.println("Thread 2 starting: " + System.currentTimeMillis());
                    ProductInventoryEntity entity = productInventoryRepository.findByIdWithPessimisticLock(productId)
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
                    entity.decreaseQuantity(20);
                    productInventoryRepository.save(entity);
                    System.out.println("Thread 2 completed: " + System.currentTimeMillis());
                    return null;
                });
            } catch (Exception e) {
                thread2Exception.set(e);
                e.printStackTrace();
            } finally {
                completeLatch.countDown();
            }
        });

        // 두 스레드 동시에 시작
        startLatch.countDown();

        // 모든 스레드가 완료될 때까지 대기
        completeLatch.await(10, TimeUnit.SECONDS);
        executor.shutdown();

        // 검증
        assertNull(thread1Exception.get(), "Thread 1 exception should be null");
        assertNull(thread2Exception.get(), "Thread 2 exception should be null");

        // 새로운 트랜잭션에서 최종 값 확인
        ProductInventoryEntity updatedProduct = txTemplate.execute(status -> {
            return productInventoryRepository.findById(productId).orElseThrow();
        });

        assertEquals(50, updatedProduct.getQuantity(), "Final quantity should be 50");
    }

    @Test
    void decraseLock() throws InterruptedException {
        // 동시 실행 쓰레드수
        int numberOfThreads = 10;

        // 각 쓰레드에서 감소시킬 재고 수량
        int decreaseAmount  = 10;

        long productId = 1;
        // ExecutorService 생성
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        // 모든 쓰레드 동시 시작
        CountDownLatch latch = new CountDownLatch(1);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        // 비동기 작업 추적을 위한 Future 리스트
        List<Runnable> tasks = new ArrayList<>();

        // 각 쓰레드가 실행할 작업 정의
        for (int i = 0; i < numberOfThreads; i++) {
            tasks.add(() -> {
                try {
                    // 모든 쓰레드가 동시에 시작하도록 대기
                    latch.await();

                    // 재고 감소 시도
                    productInventoryService.decreaseQuantityWithPessimisticLock(productId, decreaseAmount);
//                    productInventoryService.decreaseQuantity(testProductId, decreaseAmount);

                    // 성공 카운트 증가
                    successCount.incrementAndGet();
                    System.out.println("재고 감소 성공: " + Thread.currentThread().getName());
                } catch (Exception e) {
                    // 기타 예외 처리
                    System.out.println("예외 발생: " + e.getMessage());
                }
            });
        }

        // 모든 작업을 실행 큐에 제출
        for (Runnable task : tasks) {
            executorService.submit(task);
        }

        // 모든 쓰레드 동시에 시작하도록 신호
        latch.countDown();

        // 모든 쓰레드가 작업을 마칠 때까지 대기하기 위해 쓰레드 풀 종료
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(100);
        }

        // 테스트 결과 검증
        System.out.println("성공한 트랜잭션: " + successCount.get());
        System.out.println("실패한 트랜잭션: " + failCount.get());

        // 최종 재고 확인
        ProductInventoryEntity finalProduct = productInventoryService.getProductInventory(productId);
        System.out.println("최종 재고: " + finalProduct.getQuantity());
    }

}