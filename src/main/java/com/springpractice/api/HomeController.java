package com.springpractice.api;

import com.springpractice.common.annotation.BearerToken;
import com.springpractice.common.annotation.LogExecutionTime;
import com.springpractice.dtos.BearerTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @LogExecutionTime("home")
    @RequestMapping("/")
    public String home(@BearerToken BearerTokenDto bearerTokenDto) {
        return bearerTokenDto.getBearToken();
    }
}
