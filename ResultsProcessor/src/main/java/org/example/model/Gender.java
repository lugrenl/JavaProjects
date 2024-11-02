package org.example.model;

import java.util.Objects;
import java.util.stream.Stream;

public enum Gender {
    MALE("M"),
    FEMALE("F");

    private final String genderCode;

    Gender(String genderCode) {
        this.genderCode = genderCode;
    }

    /**
     * Определение пола по коду из протокола результатов.
     * </p>
     * M -> MALE, F -> FEMALE.
     * </p>
     * @param genderCode
     * @return Gender
     */
    public static Gender of(String genderCode) {
        return Stream.of(values())
                .filter(gender -> Objects.equals(gender.genderCode, genderCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown gender code: " + genderCode));
    }
}
