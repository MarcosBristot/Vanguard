package com.vanguard.vanguard.dto;

public class PortfolioDto {

    private String asset;
    private Double quantity;
    private Double currentPrice;
    private Double totalValue;

    public PortfolioDto(String asset, Double quantity, Double currentPrice, Double totalValue) {
        this.asset = asset;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.totalValue = totalValue;
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

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
}
