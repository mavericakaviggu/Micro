package com.project.departmentService.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller created to check the config server usage (able to refresh config changes without restarting the MS)
@RestController
@RefreshScope //this annotation is used to refresh the properties at runtime, it will force the below spring bean "MessageController" to refresh its value
public class MessageController {

    // annotation to read a value from the application.properties file
    @Value("${spring.boot.message}")
    private String message;

    @GetMapping("message")
    public String message() {
        return message;
    }
}
