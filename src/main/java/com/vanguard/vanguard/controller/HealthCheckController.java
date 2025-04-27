package com.vanguard.vanguard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health")
    public String health() {
        return "Vanguard está no ar! 🚀";
    }
}

