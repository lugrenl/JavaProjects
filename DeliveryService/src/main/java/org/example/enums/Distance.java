package org.example.enums;

public enum Distance {

    /**
     * @param price добавочная стоимость в рублях к базовой стоимости доставки
     * в зависимости от расстояния до пункта назначения:
     *    1. UP_T0_2KM - до 2 км
     *    2. FROM_2KM_TO_10KM - от 2 до 10 км
     *    3. FROM_10KM_TO_30KM - от 10 до 30 км
     *    4. OVER_30KM - свыше 30 км
     */

    UP_T0_2KM(50.0),
    FROM_2KM_TO_10KM(100.0),
    FROM_10KM_TO_30KM(200.0),
    OVER_30KM(300.0);

    private final double price;

    Distance(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
