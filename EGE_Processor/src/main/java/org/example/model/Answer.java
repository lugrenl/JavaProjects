package org.example.model;

import java.util.Objects;
import java.util.stream.Stream;

public enum Answer {
    A("А"),
    B("Б"),
    V("В"),
    G("Г");

    private final String answerCode;

    Answer(String answerCode) {
        this.answerCode = answerCode;
    }

    /**
     * Определение ответа по коду из протокола результатов.
     * @param answerCode
     * @return Answer
     */
    public static Answer fromCode(String answerCode) {
        return Stream.of(values())
                .filter(answer -> Objects.equals(answer.answerCode, answerCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown answer code: " + answerCode));
    }
}
