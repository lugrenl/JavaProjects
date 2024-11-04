package org.example;

import org.example.config.ResultsProcessorConfig;
import org.example.model.Distance;
import org.example.model.Gender;
import org.example.model.Result;
import org.example.service.ResultsProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ResultsProcessorMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ResultsProcessorConfig.class
        );

        ResultsProcessor resultsProcessor = applicationContext.getBean(ResultsProcessor.class);
        List<Result> fastestMen = resultsProcessor.getFastest(Gender.MALE, Distance.TEN_KM, 3);

        System.out.println(fastestMen);
    }
}