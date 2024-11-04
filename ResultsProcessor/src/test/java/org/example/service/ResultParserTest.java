package org.example.service;

import org.example.config.ResultsProcessorConfig;
import org.example.model.Distance;
import org.example.model.Gender;
import org.example.model.Result;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class ResultParserTest {

    @Test
    void parseResult() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ResultsProcessorConfig.class
        );

        ResultParser resultParser = applicationContext.getBean(ResultParser.class);

        String line = "Иван Иванов,M,10 km,55:20";
        Result result = resultParser.parseResult(line);

        assertEquals("Иван Иванов", result.person().name());
        assertEquals(Gender.MALE, result.person().gender());
        assertEquals(Distance.TEN_KM, result.distance());
        assertEquals(Duration.ofMinutes(55).plusSeconds(20), result.time());
    }
}