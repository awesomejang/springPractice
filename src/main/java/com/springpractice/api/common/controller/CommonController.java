package com.springpractice.api.common.controller;

import com.springpractice.api.common.service.AsyncExecService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common")
public class CommonController {
    private final AsyncExecService asyncExecService;

    public CommonController(AsyncExecService asyncExecService) {
        this.asyncExecService = asyncExecService;
    }

    @PostMapping("/async-job1")
    public void asyncJob1() throws InterruptedException {
        asyncExecService.asyncJob();
    }

}
