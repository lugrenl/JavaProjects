package org.example;

import org.example.config.ResultsProcessorConfig;
import org.example.model.Distance;
import org.example.model.Gender;
import org.example.model.Result;
import org.example.service.ResultsProcessor;
import org.example.service.ResultsReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ResultsProcessorMain {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ResultsProcessorConfig.class
        );

        ResultsReader resultsReader = applicationContext.getBean(ResultsReader.class);
        Path filePath = new ClassPathResource("results.csv").getFile().toPath();
        List<Result> results = resultsReader.readFromFile(filePath);

        ResultsProcessor resultsProcessor = new ResultsProcessor(results);
        List<Result> fastestMen = resultsProcessor.getFastest(Gender.MALE, Distance.TEN_KM, 3);

        System.out.println(fastestMen);
    }
}