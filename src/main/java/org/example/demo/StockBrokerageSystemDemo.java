package org.example.demo;

import org.example.models.*;
import org.example.exceptions.OrderExecutionException;

public class StockBrokerageSystemDemo {
    public static void run() {
        StockBroker stockBroker = StockBroker.getInstance();

        // Create user and account
        User user = new User("U001", "John Doe", "john@example.com");
        Account account = stockBroker.createAccount("A001", user, 10000.0);

        // Add stocks to the stock broker
        Stock stock1 = new Stock("AAPL", "Apple Inc.", 150.0);
        Stock stock2 = new Stock("GOOGL", "Alphabet Inc.", 2000.0);
        stockBroker.addStock(stock1);
        stockBroker.addStock(stock2);

        try {
            // Place buy orders
            Order buyOrder1 = new BuyOrder("O001", account, stock1, 10, 150.0);
            Order buyOrder2 = new BuyOrder("O002", account, stock2, 2, 2000.0); // Reduced quantity to stay within $10,000 balance
            stockBroker.placeOrder(buyOrder1);
            stockBroker.placeOrder(buyOrder2);

            // Wait a moment for orders to process
            Thread.sleep(100);

            // Place sell orders
            Order sellOrder1 = new SellOrder("O003", account, stock1, 5, 160.0);
            stockBroker.placeOrder(sellOrder1);

            // Print account balance and portfolio
            System.out.println("\n=== Final Account Status ===");
            System.out.println("Account Balance: $" + account.getBalance());
            System.out.println("Portfolio Holdings: " + account.getPortfolio().getHoldings());

        } catch (InterruptedException e) {
            System.out.println("Demo interrupted: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        run();
    }
}