package org.example.enums;

public enum Fragile {

    /**
     * @param price добавочная стоимость в рублях в зависимости от хрупкости груза:
     *    1. NOT_FRAGILE - не хрупкий
     *    2. FRAGILE - хрупкий
     */

    NOT_FRAGILE(0.0),
    FRAGILE(300.0);

    private final double price;

    Fragile(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
