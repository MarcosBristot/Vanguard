package com.vanguard.vanguard.service;

import com.vanguard.vanguard.dto.PerformanceDto;
import com.vanguard.vanguard.dto.PortfolioDto;
import com.vanguard.vanguard.model.Transaction;
import com.vanguard.vanguard.model.User;
import com.vanguard.vanguard.repository.TransactionRepository;
import com.vanguard.vanguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class PortfolioService {

    private final TransactionRepository transactionRepository;
    private final PriceService priceService;
    private final UserRepository userRepository;  // Injeção do UserRepository

    @Autowired
    public PortfolioService(TransactionRepository transactionRepository, PriceService priceService, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.priceService = priceService;
        this.userRepository = userRepository;
    }

    public List<PortfolioDto> getUserPortfolio(Long userId) {
        // Verificar se o usuário existe no banco de dados
        Optional<User> user = userRepository.findById(userId);

        // Se o usuário não for encontrado, lançar uma exceção 404
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

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

    public List<PerformanceDto> getPortfolioPerformance(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");  // Exceção quando o usuário não existe
        }

        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        if (transactions.isEmpty()) {
            return Collections.emptyList();  // Retorna lista vazia caso não haja transações
        }

        Map<String, Double> assetQuantities = new HashMap<>();
        Map<String, Double> assetInvestments = new HashMap<>();

        for (Transaction transaction : transactions) {
            String asset = transaction.getAsset();
            double quantity = transaction.getQuantity() * (transaction.getTransactionType().equalsIgnoreCase("BUY") ? 1 : -1);
            double total = transaction.getUnitPrice() * transaction.getQuantity();

            assetQuantities.put(asset, assetQuantities.getOrDefault(asset, 0.0) + quantity);
            assetInvestments.put(asset, assetInvestments.getOrDefault(asset, 0.0) + (transaction.getTransactionType().equalsIgnoreCase("BUY") ? total : -total));
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