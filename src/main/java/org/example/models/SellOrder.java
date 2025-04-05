package org.example.models;

import org.example.exceptions.InsufficientStockException;
import org.example.exceptions.OrderExecutionException;

public class SellOrder extends Order {
    public SellOrder(String orderId, Account account, Stock stock, int quantity, double price) {
        super(orderId, account, stock, quantity, price);
    }

    @Override
    public synchronized void execute() throws OrderExecutionException {
        try {
            double totalProceeds = getQuantity() * getPrice();

            synchronized (getAccount()) {
                getAccount().removeFromPortfolio(getStock().getSymbol(), getQuantity());
                getAccount().deposit(totalProceeds);
            }

            setStatus(OrderStatus.EXECUTED);
        } catch (InsufficientStockException e) {
            setStatus(OrderStatus.REJECTED);
            throw new OrderExecutionException("Sell order failed: " + e.getMessage(), e);
        } catch (Exception e) {
            setStatus(OrderStatus.REJECTED);
            throw new OrderExecutionException("Sell order failed", e);
        }
    }
}