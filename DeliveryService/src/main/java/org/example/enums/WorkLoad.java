package org.example.enums;

public enum WorkLoad {

    /**
     * @param ratio коэффициент нагрузки умножается на стоимость доставки:
     *    1. NORMAL - нормальная
     *    2. INTENSE - интенсивная
     *    3. HEAVY - тяжелая
     *    4. VERY_HEAVY - очень тяжелая
     */

    NORMAL(1.0),
    INTENSE(1.2),
    HEAVY(1.4),
    VERY_HEAVY(1.6);

    private final double ratio;

    WorkLoad(double ratio) {
        this.ratio = ratio;
    }

    public double getRatio() {
        return ratio;
    }
}
