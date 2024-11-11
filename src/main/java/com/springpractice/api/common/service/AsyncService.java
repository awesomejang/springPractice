package com.springpractice.api.common.service;

import com.springpractice.common.advice.RestExceptionControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    private final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    public void asyncMethod() {
        logger.debug("Async Method Start");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error("Async Method Error", e);
        }
        logger.debug("Async Method End");
    }
}
