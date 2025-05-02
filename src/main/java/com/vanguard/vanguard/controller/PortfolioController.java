package com.vanguard.vanguard.controller;

import com.vanguard.vanguard.dto.PerformanceDto;
import com.vanguard.vanguard.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PerformanceDto>> getPortfolio(@PathVariable Long userId) {
        try {
            List<PerformanceDto> portfolio = portfolioService.getPortfolioPerformance(userId);
            if (portfolio.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(portfolio);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/performance/{userId}")
    public ResponseEntity<List<PerformanceDto>> getPortfolioPerformance(@PathVariable Long userId) {
        List<PerformanceDto> performance = portfolioService.getPortfolioPerformance(userId);
        return ResponseEntity.ok(performance);
    }
}

