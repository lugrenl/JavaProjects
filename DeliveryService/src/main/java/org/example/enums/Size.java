package org.example.enums;

public enum Size {

    /**
     * @param price добавочная стоимость в рублях к базовой стоимости доставки
     * в зависимости от габаритов груза:
     *    1. SMALL - малые габариты
     *    2. LARGE - большие габариты
     */

    SMALL(100.0),
    LARGE(200.0);

    private final double price;

    Size(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
