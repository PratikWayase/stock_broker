package org.example.models;

import org.example.exceptions.InsufficientStockException;
import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private final Account account;
    private final Map<String, Integer> holdings;

    public Portfolio(Account account) {
        this.account = account;
        this.holdings = new HashMap<>();
    }

    public void addStock(Stock stock, int quantity) {
        if (stock == null) throw new IllegalArgumentException("Stock cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");

        holdings.merge(stock.getSymbol(), quantity, Integer::sum);
    }

    public void removeStock(String symbol, int quantity) throws InsufficientStockException {
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("Stock symbol cannot be null or empty");
        }
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");

        int currentQuantity = holdings.getOrDefault(symbol, 0);
        if (currentQuantity < quantity) {
            throw new InsufficientStockException(
                    String.format("Insufficient shares of %s. Requested: %d, Available: %d",
                            symbol, quantity, currentQuantity));
        }

        holdings.put(symbol, currentQuantity - quantity);

        if (currentQuantity - quantity == 0) {
            holdings.remove(symbol);
        }
    }

    public int getQuantity(String symbol) {
        return holdings.getOrDefault(symbol, 0);
    }

    public Map<String, Integer> getHoldings() {
        return new HashMap<>(holdings);
    }
}