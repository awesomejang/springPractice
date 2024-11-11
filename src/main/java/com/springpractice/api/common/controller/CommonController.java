package com.springpractice.api.common.controller;

import com.springpractice.api.common.service.AsyncExecService;
import com.springpractice.dtos.CommonResponseDto;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommonResponseDto<Void>> asyncJob1() throws InterruptedException {
        asyncExecService.asyncJob();

        return ResponseEntity.ok(CommonResponseDto.success());
    }

}
