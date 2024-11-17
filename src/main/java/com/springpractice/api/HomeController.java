package com.springpractice.api;

import com.springpractice.common.annotation.BearerToken;
import com.springpractice.common.annotation.LogExecutionTime;
import com.springpractice.dtos.BearerTokenDto;
import com.springpractice.dtos.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @LogExecutionTime("home")
    @RequestMapping("/")
    public ResponseEntity<CommonResponseDto<Void>> home() {
        return ResponseEntity.ok(CommonResponseDto.success(null));
    }
}
