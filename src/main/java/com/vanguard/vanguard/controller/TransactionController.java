package com.vanguard.vanguard.controller;

import com.vanguard.vanguard.model.Transaction;
import com.vanguard.vanguard.repository.TransactionRepository;
import com.vanguard.vanguard.repository.UserRepository;
import com.vanguard.vanguard.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PriceService priceService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactionsByUser(@PathVariable Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    @GetMapping("/price/{asset}")
    public Double getAssetPrice(@PathVariable String asset) {
        return priceService.getCryptoPrice(asset.toLowerCase());
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        if (transaction.getUnitPrice() == null || transaction.getUnitPrice() <= 0) {
            transaction.setUnitPrice(priceService.getCryptoPrice(transaction.getAsset().toLowerCase()));
        }
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        transactionRepository.delete(transaction);
    }
}

