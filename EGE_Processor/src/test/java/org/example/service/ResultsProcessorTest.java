package org.example.service;

import org.example.config.ResultsProcessorConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class ResultsProcessorTest {

    @Test
    void getScore() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ResultsProcessorConfig.class
        );

        ResultsProcessor resultsProcessor = applicationContext.getBean(ResultsProcessor.class);

        int score = resultsProcessor.getScore();
        assertEquals(10, score);
    }
}