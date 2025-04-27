package com.vanguard.vanguard.service;

import com.vanguard.vanguard.dto.PortfolioDto;
import com.vanguard.vanguard.model.Transaction;
import com.vanguard.vanguard.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    private final TransactionRepository transactionRepository;
    private final PriceService priceService;

    public PortfolioService(TransactionRepository transactionRepository, PriceService priceService) {
        this.transactionRepository = transactionRepository;
        this.priceService = priceService;
    }

    public List<PortfolioDto> getUserPortfolio(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        Map<String, Double> assetQuantities = new HashMap<>();

        // Consolidar quantidade de cada ativo
        for (Transaction transaction : transactions) {
            String asset = transaction.getAsset();
            double quantity = transaction.getQuantity() * (transaction.getTransactionType().equalsIgnoreCase("BUY") ? 1 : -1);
            assetQuantities.put(asset, assetQuantities.getOrDefault(asset, 0.0) + quantity);
        }

        // Criar a lista de PortfolioDto
        List<PortfolioDto> portfolio = new ArrayList<>();
        for (Map.Entry<String, Double> entry : assetQuantities.entrySet()) {
            String asset = entry.getKey();
            Double quantity = entry.getValue();
            if (quantity > 0) {
                Double currentPrice = priceService.getCryptoPrice(asset.toLowerCase());
                portfolio.add(new PortfolioDto(asset, quantity, currentPrice, quantity * currentPrice));
            }
        }

        return portfolio;
    }
}
