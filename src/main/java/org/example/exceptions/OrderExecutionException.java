package org.example.exceptions;

public class OrderExecutionException extends Exception {
    public OrderExecutionException(String message) {
        super(message);
    }

    public OrderExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}