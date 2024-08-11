package com.springpractice.api.log;

import com.springpractice.dtos.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/log")
@Slf4j
public class WebLogController {

    @GetMapping("")
    @ResponseBody
    public CommonResponseDto<String> GetRequest() {
        return CommonResponseDto.success("GET request success");
    }

    @PostMapping
    public CommonResponseDto<String> PostRequest(@RequestBody String requestData) {
        log.info("Request data: {}", requestData);
        return CommonResponseDto.success("POST request success");
    }
}
