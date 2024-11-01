package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

public class MoviePlayer {
    private final List<Movie> movieList;

    @Value("${moviePlayer.name}")
    private String name;

    @Value("${moviePlayer.volume}")
    private int volume;

    public MoviePlayer(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public int getVolume() {
        return volume;
    }

    public String playMovie() {
        Random random = new Random();
        return "Watching: " + movieList.get(random.nextInt(movieList.size())).getMovie()
                + " Volume: " + volume;
    }
}