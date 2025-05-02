package com.vanguard.vanguard.controller;

import com.vanguard.vanguard.dto.PerformanceDto;
import com.vanguard.vanguard.service.PortfolioService;
import org.springframework.http.HttpStatus;
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

    // Endpoint para obter o portfolio de um usuário
    @GetMapping("/{userId}")
    public ResponseEntity<List<PerformanceDto>> getPortfolio(@PathVariable Long userId) {
        try {
            List<PerformanceDto> portfolio = portfolioService.getPortfolioPerformance(userId);

            if (portfolio.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 No Content
            }
            return ResponseEntity.ok(portfolio);  // 200 OK com dados

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // 500 Internal Server Error
        }
    }

    // Endpoint para obter a performance de um portfolio
    @GetMapping("/performance/{userId}")
    public ResponseEntity<List<PerformanceDto>> getPortfolioPerformance(@PathVariable Long userId) {
        try {
            List<PerformanceDto> performance = portfolioService.getPortfolioPerformance(userId);
            if (performance.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 No Content
            }
            return ResponseEntity.ok(performance);  // 200 OK com dados
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // 404 User Not Found caso a exceção seja disparada
        }
    }
}
