package org.example.models;

import org.example.exceptions.InsufficientFundsException;
import org.example.exceptions.InsufficientStockException;

import java.util.Objects;

public class Account {
    private final String accountId;
    private final User user;
    private double balance;
    private final Portfolio portfolio;

    public Account(String accountId, User user, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.accountId = Objects.requireNonNull(accountId);
        this.user = Objects.requireNonNull(user);
        this.balance = initialBalance;
        this.portfolio = new Portfolio(this);
    }

    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    public synchronized void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance < amount) {
            throw new InsufficientFundsException("Insufficient funds in account");
        }
        balance -= amount;
    }

    public synchronized void addToPortfolio(Stock stock, int quantity) {
        portfolio.addStock(stock, quantity);
    }

    public synchronized void removeFromPortfolio(String symbol, int quantity)
            throws InsufficientStockException {
        portfolio.removeStock(symbol, quantity);
    }

    public synchronized boolean hasSufficientStock(String symbol, int quantity) {
        return portfolio.getQuantity(symbol) >= quantity;
    }

    // Getters
    public String getAccountId() { return accountId; }
    public User getUser() { return user; }
    public double getBalance() { return balance; }
    public Portfolio getPortfolio() { return portfolio; }
}
