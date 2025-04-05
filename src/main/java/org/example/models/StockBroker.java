package org.example.models;

import org.example.exceptions.InsufficientFundsException;
import org.example.exceptions.InsufficientStockException;
import org.example.exceptions.OrderExecutionException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class StockBroker {
    private static StockBroker instance;
    private final Map<String, Account> accounts;
    private final Map<String, Stock> stocks;
    private final Queue<Order> orderQueue;
    private final AtomicInteger accountIdCounter;

    private StockBroker() {
        accounts = new ConcurrentHashMap<>();
        stocks = new ConcurrentHashMap<>();
        orderQueue = new ConcurrentLinkedQueue<>();
        accountIdCounter = new AtomicInteger();
    }

    public static synchronized StockBroker getInstance() {
        if (instance == null) {
            instance = new StockBroker();
        }
        return instance;
    }

    public Account createAccount(String accountId, User user, double initialBalance) {
        Account account = new Account(accountId, user, initialBalance);
        accounts.put(accountId, account);
        return account;
    }

    public void addStock(Stock stock) {
        stocks.put(stock.getSymbol(), stock);
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public void placeOrder(Order order) {
        orderQueue.offer(order);
        processOrders();
    }

    private void processOrders() {
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            try {
                order.execute();
                System.out.println("Order executed: " + order.getOrderId());
            } catch (OrderExecutionException e) {
                System.out.println("Order failed: " + order.getOrderId() + " - " + e.getMessage());
            }
        }
    }

    private String generateAccountID() {
        int id = accountIdCounter.getAndIncrement();
        return "A" + String.format("%03d", id);
    }
}