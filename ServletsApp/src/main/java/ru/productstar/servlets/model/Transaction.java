package ru.productstar.servlets.model;

public final class Transaction {

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
