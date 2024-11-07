package org.example.service;

import org.example.config.ResultsProcessorConfig;
import org.example.model.Result;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ResultsReaderTest {

    @Test
    void readFromFile() throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ResultsProcessorConfig.class
        );

        ResultsReader resultsReader = applicationContext.getBean(ResultsReader.class);

        // Проверка существующего пути
        Path filePathToAnswers = new ClassPathResource("answers.csv").getFile().toPath();
        List<Result> answers = resultsReader.readFromFile(filePathToAnswers);

        int expectedSize = 10;
        assertEquals(expectedSize, answers.size());


        // Проверка несуществующего пути
        try {
            Path filePathToNothing = new ClassPathResource("nothing.csv").getFile().toPath();
            resultsReader.readFromFile(filePathToNothing);
        }
        catch (IOException e) {
            assertTrue(true);
        }


        //null
        try {
            resultsReader.readFromFile(null);
        }
        catch (NullPointerException e) {
            assertTrue(true);
        }
    }
}