package com.springpractice.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OptimisticLockRetry {
    int maxRetries() default 3;
    int initialDelay() default 100;
    double backoffFactor() default 0.2;
}
