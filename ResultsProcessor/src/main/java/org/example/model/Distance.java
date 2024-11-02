package org.example.model;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

public enum Distance {
    FIVE_KM("5 km"),
    TEN_KM("10 km");

    private final String distanceCode;

    Distance(String distanceCode) {
        this.distanceCode = distanceCode;
    }

    /**
     * Дистанция по коду из протокола результатов.
     * </p>
     * 5 km -> FIVE_KM, 10 km -> TEN_KM.
     * </p>
     * @param distanceCode
     * @return Distance
     */
    public static Distance of(String distanceCode) {
        return Stream.of(values())
                .filter(distance -> Objects.equals(distance.distanceCode, distanceCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown distance code: " + distanceCode));
    }


}
