package org.example.bookstore.models;

import lombok.Setter;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Book {
    private final String id = UUID.randomUUID().toString();

    @Setter
    private String title;

    @Setter
    private String author;

    @Setter
    private String genre;

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
}