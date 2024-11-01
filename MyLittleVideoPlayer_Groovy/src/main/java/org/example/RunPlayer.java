package org.example;

import org.springframework.context.support.GenericGroovyApplicationContext;

public class RunPlayer{
    public static void main(String[] args) {
        GenericGroovyApplicationContext context = new GenericGroovyApplicationContext();
        context.load("classpath:SpringConfig.groovy");
        context.refresh();

        // Получение бинов из контекста
        Computer computer = context.getBean("computer", Computer.class);

        // Использование бинов
        System.out.println(computer.runMoviePlayer());

        // Закрытие контекста
        context.close();
    }
}

