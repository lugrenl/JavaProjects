package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Computer {
    private final int id;
    private final MoviePlayer moviePlayer;

    public Computer(MoviePlayer moviePlayer) {
        this.id = 1;
        this.moviePlayer = moviePlayer;
    }

    public String runMoviePlayer() {
        return "Computer " + "with id:" + id + " " + moviePlayer.playMovie();
    }
}
