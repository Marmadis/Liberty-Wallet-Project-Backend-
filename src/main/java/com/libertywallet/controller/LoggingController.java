package com.libertywallet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoggingController {

    @GetMapping("/log")
    public String logExample(@RequestParam String name) {
        log.info("Request received with name: {}", name);
        log.debug("Debugging request for name: {}", name);
        log.warn("Potential issue with request: {}", name);
        log.error("Unexpected error occurred for user: {}", name);

        return "Hello, " + name;
    }
}
