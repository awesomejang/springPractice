package com.springpractice.common.advice;

import com.springpractice.common.annotation.OptimisticLockRetry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OptimisticLockRetryAspect {

    @Around(value = "@annotation(retry)")
    public Object retryOnOptimisticLock(ProceedingJoinPoint joinPoint, OptimisticLockRetry retry) throws Throwable {
        int retryCount = 0;
        int maxRetries = retry.maxRetries();
        long backoff = retry.initialDelay();
        double multiplier = retry.maxRetries();

        while (true) {
            try {
                return joinPoint.proceed();
            } catch (ObjectOptimisticLockingFailureException e) {
                retryCount++;
                if (retryCount >= maxRetries) {
                    throw new IllegalStateException("주문에 실패했습니다.");
                }
                log.info("[RETRY COUNT = {}", retryCount);
                Thread.sleep((long)(backoff * Math.pow(multiplier, retryCount - 1)));
            }
        }
    }
}
