package ru.productstar.servlets.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public final class Transaction {

    @NotEmpty(message = "Transaction name must not be empty")
    @Size(min = 2, max = 30, message = "Transaction name must be between 2 and 30 characters")
    private final String name;

    private final int value;
    private final TransactionType type;

    public Transaction(String name, int value, TransactionType type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", type=" + type +
                '}';
    }
}
