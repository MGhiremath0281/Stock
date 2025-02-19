package com.stocktracker.model;

public class StockPrice {
    private String symbol;
    private double price;
    private String timestamp;

    public StockPrice() {
    }

    public StockPrice(String symbol, double price, String timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s: $%.2f at %s", symbol, price, timestamp);
    }
}