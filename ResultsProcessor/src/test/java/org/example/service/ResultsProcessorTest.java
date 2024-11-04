package org.example.service;

import org.example.config.ResultsProcessorConfig;
import org.example.model.Distance;
import org.example.model.Gender;
import org.example.model.Person;
import org.example.model.Result;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultsProcessorTest {

    @Test
    void getFastest() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ResultsProcessorConfig.class
        );

        ResultsProcessor resultsProcessor = applicationContext.getBean(ResultsProcessor.class);

        Gender gender = Gender.FEMALE;
        Distance distance = Distance.FIVE_KM;
        List<Result> fastestWomen = resultsProcessor.getFastest(gender, distance, 1);

        Person expectedPerson = new Person("Корабкина Хелена", Gender.FEMALE);

        assertEquals(1, fastestWomen.size());
        assertEquals(gender, fastestWomen.getFirst().person().gender());
        assertEquals(expectedPerson, fastestWomen.getFirst().person());

        String expectedMinutes = "16";
        String expectedSeconds = "49";
        int expectedTotalSeconds = Integer.parseInt(expectedMinutes) * 60 + Integer.parseInt(expectedSeconds);
        Duration expectedTime = Duration.ofSeconds(expectedTotalSeconds);

        assertEquals(expectedTime, fastestWomen.getFirst().time());
    }
}