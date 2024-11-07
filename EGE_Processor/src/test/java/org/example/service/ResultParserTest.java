package org.example.service;

import org.example.config.ResultsProcessorConfig;
import org.example.model.Answer;
import org.example.model.Result;
import org.example.model.Question;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
class ResultParserTest {

    @Test
    void parseResult() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ResultsProcessorConfig.class
        );

        ResultParser resultParser = applicationContext.getBean(ResultParser.class);

        // Правильная строка
        String goodLine = "1 - А";
        Question q = Question.fromCode("1");
        Answer a = Answer.fromCode("А");
        Result result = resultParser.parseResult(goodLine);

        assertEquals(q, result.question());
        assertEquals(a, result.answer());


        // Ошибка в номере вопроса
        String baqQuestion = "11 - А";

        try {
            resultParser.parseResult(baqQuestion);
        } catch (Exception e) {
            assertEquals("Unknown question code: 11", e.getMessage());
        }


        // Ошибка в букве ответа
        String badAnswer = "10 - H";

        try {
            resultParser.parseResult(badAnswer);
        } catch (Exception e) {
            assertEquals("Unknown answer code: H", e.getMessage());
        }


        // Ошибка в разделителе
        String badSeparator = "10 = H";

        try {
            resultParser.parseResult(badSeparator);
        } catch (Exception e) {
            assertEquals("Unknown question code: 10 = H", e.getMessage()); // Воспринимает как ошибку в вопросе
        }


        // Пустая строка
        String emptyLine = "";
        try {
            resultParser.parseResult(emptyLine);
        } catch (Exception e) {
            assertEquals("Unknown question code: ", e.getMessage()); // Воспринимает как ошибку в вопросе
        }


        // Null строка
        try {
            resultParser.parseResult(null);
        } catch (Exception e) {
            assertEquals("Cannot invoke \"String.split(String)\" because \"line\" is null", e.getMessage());
        }
    }
}