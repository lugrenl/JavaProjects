package org.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class RunPlayer {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//                "applicationContext.xml"
//        );

//        Computer computer = context.getBean("computerBean", Computer.class);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "annotationApplicationContext.xml"
        );

        Computer computer = context.getBean("computer", Computer.class);
        System.out.println(computer.runMoviePlayer());
        context.close();
    }
}