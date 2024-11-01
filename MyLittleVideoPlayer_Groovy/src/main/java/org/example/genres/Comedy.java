package org.example.genres;

import org.example.Movie;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Comedy implements Movie {
    @PostConstruct
    public void init() {
        System.out.println("Load film to playlist " + this.getMovie());
    }
    @PreDestroy
    public void destroy() {
        System.out.println("Unload film from playlist " + this.getMovie());
    }

    @Override
    public String getMovie() {
        return "The Mask";
    }
}