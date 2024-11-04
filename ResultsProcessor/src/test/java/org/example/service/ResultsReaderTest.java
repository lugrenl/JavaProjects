package org.example.service;

import org.example.model.Distance;
import org.example.model.Gender;
import org.example.model.Result;
import org.example.config.ResultsProcessorConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultsReaderTest {

    @Test
    void readFromFile() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ResultsProcessorConfig.class
        );

        ResultsReader resultsReader = applicationContext.getBean(ResultsReader.class);
        List<Result> results = resultsReader.readFromFile();

        int expectedSize = 40;
        assertEquals(expectedSize, results.size());

        Result result = results.getFirst();
        assertEquals("Ахмадеев Ринас", result.person().name());
        assertEquals(Gender.MALE, result.person().gender());
        assertEquals(Distance.TEN_KM, result.distance());
        assertEquals(Duration.ofMinutes(29).plusSeconds(1), result.time());
    }
}