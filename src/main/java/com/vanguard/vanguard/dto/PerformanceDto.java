package com.vanguard.vanguard.dto;

public class PerformanceDto {

    private String asset;
    private Double quantity;
    private Double totalInvested;
    private Double currentPrice;
    private Double currentValue;
    private Double gainLoss;
    private Double returnPercentage;

    // Construtor
    public PerformanceDto(String asset, Double quantity, Double totalInvested,
                          Double currentPrice, Double currentValue, Double gainLoss, Double returnPercentage) {
        this.asset = asset;
        this.quantity = quantity;
        this.totalInvested = totalInvested;
        this.currentPrice = currentPrice;
        this.currentValue = currentValue;
        this.gainLoss = gainLoss;
        this.returnPercentage = returnPercentage;
    }

    // Getters e Setters
    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTotalInvested() {
        return totalInvested;
    }

    public void setTotalInvested(Double totalInvested) {
        this.totalInvested = totalInvested;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getGainLoss() {
        return gainLoss;
    }

    public void setGainLoss(Double gainLoss) {
        this.gainLoss = gainLoss;
    }

    public Double getReturnPercentage() {
        return returnPercentage;
    }

    public void setReturnPercentage(Double returnPercentage) {
        this.returnPercentage = returnPercentage;
    }
}
