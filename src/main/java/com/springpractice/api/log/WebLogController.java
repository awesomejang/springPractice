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
        return CommonResponseDto.success("POST request success");
    }

    @PostMapping("/test")
    public CommonResponseDto<String> logTest(@RequestBody String requestData) {
        log.info("=====시작=======");
        log.info("=====중간=======");
        log.info("=====종료=======");
        return CommonResponseDto.success("POST request success");
    }
}
