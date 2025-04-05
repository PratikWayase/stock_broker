package org.example.models;

import org.example.exceptions.OrderExecutionException;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Order {
    private final String orderId;
    private final Account account;
    private final Stock stock;
    private final int quantity;
    private final double price;
    private OrderStatus status;
    private LocalDateTime executionTime;
    private String failureReason;

    public Order(String orderId, Account account, Stock stock, int quantity, double price) {
        this.orderId = Objects.requireNonNull(orderId);
        this.account = Objects.requireNonNull(account);
        this.stock = Objects.requireNonNull(stock);
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (price <= 0) throw new IllegalArgumentException("Price must be positive");
        this.quantity = quantity;
        this.price = price;
        this.status = OrderStatus.PENDING;
    }

    public abstract void execute() throws OrderExecutionException;

    // Getters and Setters
    public String getOrderId() { return orderId; }
    public Account getAccount() { return account; }
    public Stock getStock() { return stock; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public OrderStatus getStatus() { return status; }
    protected void setStatus(OrderStatus status) { this.status = status; }
    public LocalDateTime getExecutionTime() { return executionTime; }
    public String getFailureReason() { return failureReason; }
}