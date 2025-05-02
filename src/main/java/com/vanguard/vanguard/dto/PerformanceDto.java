package com.vanguard.vanguard.dto;

public class PerformanceDto {

    private String asset;
    private Double quantity;
    private Double totalInvested;
    private Double currentPrice;
    private Double currentValue;
    private Double gainLoss;
    private Double returnPercentage;

    // Construtores, getters e setters

    public PerformanceDto(String asset, Double quantity, Double totalInvested, Double currentPrice,
                          Double currentValue, Double gainLoss, Double returnPercentage) {
        this.asset = asset;
        this.quantity = quantity;
        this.totalInvested = totalInvested;
        this.currentPrice = currentPrice;
        this.currentValue = currentValue;
        this.gainLoss = gainLoss;
        this.returnPercentage = returnPercentage;
    }

    // Getters e Setters
}
