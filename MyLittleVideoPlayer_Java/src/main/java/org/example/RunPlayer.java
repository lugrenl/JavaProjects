package org.example;

import org.example.config.AnnotationSpringConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class RunPlayer {
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//                SpringConfig.class
//        );

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                AnnotationSpringConfig.class
        );
        Computer computer = context.getBean("computer", Computer.class);
        System.out.println(computer.runMoviePlayer());
        context.close();
    }
}