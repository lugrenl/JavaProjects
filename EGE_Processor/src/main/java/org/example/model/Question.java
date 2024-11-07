package org.example.model;

import java.util.Objects;
import java.util.stream.Stream;

public enum Question {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10");

    private final String questionCode;

    Question(String questionCode) {
        this.questionCode = questionCode;
    }


    /**
     * Определение номера вопроса по коду из протокола результатов.
     * @param questionCode
     * @return Question
     */
    public static Question fromCode(String questionCode) {
        return Stream.of(values())
                .filter(question -> Objects.equals(question.questionCode, questionCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown question code: " + questionCode));
    }
}
