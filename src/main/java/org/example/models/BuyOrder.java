package org.example.models;

import org.example.exceptions.InsufficientFundsException;
import org.example.exceptions.OrderExecutionException;

public class BuyOrder extends Order {
    public BuyOrder(String orderId, Account account, Stock stock, int quantity, double price) {
        super(orderId, account, stock, quantity, price);
    }

    @Override
    public synchronized void execute() throws OrderExecutionException {
        try {
            double totalCost = getQuantity() * getPrice();

            synchronized (getAccount()) {
                getAccount().withdraw(totalCost);
                getAccount().addToPortfolio(getStock(), getQuantity());
            }

            setStatus(OrderStatus.EXECUTED);
        } catch (InsufficientFundsException e) {
            setStatus(OrderStatus.REJECTED);
            throw new OrderExecutionException("Buy order failed: " + e.getMessage(), e);
        } catch (Exception e) {
            setStatus(OrderStatus.REJECTED);
            throw new OrderExecutionException("Buy order failed", e);
        }
    }
}