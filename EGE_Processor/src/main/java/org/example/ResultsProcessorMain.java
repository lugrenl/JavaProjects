package org.example;

import org.example.config.ResultsProcessorConfig;
import org.example.service.ResultsProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ResultsProcessorMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ResultsProcessorConfig.class
        );
        ResultsProcessor resultsProcessor = applicationContext.getBean(ResultsProcessor.class);
        System.out.println(resultsProcessor.getScore());
        applicationContext.close();
    }
}