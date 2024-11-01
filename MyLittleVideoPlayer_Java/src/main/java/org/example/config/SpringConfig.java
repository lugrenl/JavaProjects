package org.example.config;

import org.example.Computer;
import org.example.Movie;
import org.example.MoviePlayer;
import org.example.genres.Action;
import org.example.genres.Comedy;
import org.example.genres.Drama;
import org.example.genres.Thriller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource("classpath:moviePlayer.properties")
public class SpringConfig {
    @Bean
    public Action action() {
        return new Action();
    }

    @Bean
    public Comedy comedy() {
        return new Comedy();
    }

    @Bean
    public Drama drama() {
        return new Drama();
    }

    @Bean
    public Thriller thriller() {
        return new Thriller();
    }

    @Bean
    public List<Movie> movieList() {
        return Arrays.asList(action(), comedy(), drama(), thriller());
    }

    @Bean
    @Scope("prototype")
    public MoviePlayer moviePlayer() {
        return new MoviePlayer(movieList());
    }

    @Bean
    public Computer computer() {
        return new Computer(moviePlayer());
    }
}
