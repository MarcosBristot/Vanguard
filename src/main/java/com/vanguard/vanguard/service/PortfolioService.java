package com.vanguard.vanguard.service;

import com.vanguard.vanguard.dto.PerformanceDto;
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
        // Buscar transações do usuário
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        if (transactions.isEmpty()) {
            return Collections.emptyList();
        }

        // Consolidar quantidade de cada ativo
        Map<String, Double> assetQuantities = new HashMap<>();
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
                Double currentPrice;
                try {
                    currentPrice = priceService.getCryptoPrice(asset.toLowerCase());
                } catch (Exception e) {
                    currentPrice = 0.0; // Valor padrão em caso de erro
                }
                portfolio.add(new PortfolioDto(asset, quantity, currentPrice, quantity * currentPrice));
            }
        }

        return portfolio;
    }

    public List<PerformanceDto> getPortfolioPerformance(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        Map<String, Double> assetQuantities = new HashMap<>();
        Map<String, Double> assetInvestments = new HashMap<>();

        for (Transaction transaction : transactions) {
            String asset = transaction.getAsset();
            double quantity = transaction.getQuantity() * (transaction.getTransactionType().equalsIgnoreCase("BUY") ? 1 : -1);
            double total = transaction.getUnitPrice() * transaction.getQuantity(); // Alterado para usar getUnitPrice

            assetQuantities.put(asset, assetQuantities.getOrDefault(asset, 0.0) + quantity);
            assetInvestments.put(asset, assetInvestments.getOrDefault(asset, 0.0) +
                    (transaction.getTransactionType().equalsIgnoreCase("BUY") ? total : -total));
        }

        List<PerformanceDto> performanceList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : assetQuantities.entrySet()) {
            String asset = entry.getKey();
            Double quantity = entry.getValue();
            if (quantity > 0) {
                Double currentPrice = priceService.getCryptoPrice(asset.toLowerCase());
                Double totalInvested = assetInvestments.get(asset);
                Double currentValue = quantity * currentPrice;
                Double gainLoss = currentValue - totalInvested;
                Double returnPercentage = (gainLoss / totalInvested) * 100;

                performanceList.add(new PerformanceDto(asset, quantity, totalInvested, currentPrice, currentValue, gainLoss, returnPercentage));
            }
        }

        return performanceList;
    }

}
