package com.springpractice.api.log;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/log")
public class WebLogController {

    public String log() {
        return "Logging";
    }

}
