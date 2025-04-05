package org.example.models;

public class Stock {
    private final String symbol;
    private final String name;
    private double price;

    public Stock(String symbol, String name, double price) {
        if (symbol == null || symbol.isEmpty()) throw new IllegalArgumentException("Symbol cannot be null or empty");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty");
        if (price <= 0) throw new IllegalArgumentException("Price must be positive");

        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    public synchronized void updatePrice(double newPrice) {
        if (newPrice <= 0) throw new IllegalArgumentException("Price must be positive");
        this.price = newPrice;
    }

    // Getters
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public synchronized double getPrice() { return price; }
}
