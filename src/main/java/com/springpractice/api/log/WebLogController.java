package com.springpractice.api.log;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/log")
public class WebLogController {

    @GetMapping("")
    public String log() {
        return "Logging";
    }

}
