package org.example.service;

import org.example.config.ResultsProcessorConfig;
import org.example.model.Result;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ResultsReaderTest {

    @Test
    void readFromFile() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ResultsProcessorConfig.class
        );

        ResultsReader resultsReader = applicationContext.getBean(ResultsReader.class);

        // Проверка существующего пути
        List<Result> answers = resultsReader.readFromFile("answers.csv");

        int expectedSize = 10;
        assertEquals(expectedSize, answers.size());


        // Проверка несуществующего пути
        try {
            resultsReader.readFromFile("nothing.csv");
        }
        catch (UncheckedIOException e) {
            assertTrue(true);
        }


        //null
        try {
            resultsReader.readFromFile(null);
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
}