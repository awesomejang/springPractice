package com.springpractice.api.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AsyncExecService {

    private final Logger logger = LoggerFactory.getLogger(AsyncExecService.class);

    public void asyncJob() throws InterruptedException {
        logger.info("AsyncExecService.asyncJob start");
        Thread.sleep(2000);

        logger.info("AsyncExecService.asyncJob Step 1");
        Thread.sleep(3000);

        logger.info("AsyncExecService.asyncJob end");
    }
}
