package org.example.bookstore.models;

import lombok.Setter;
import lombok.Getter;

import java.util.UUID;

@Getter
@Setter
public class Book {
    private String id = UUID.randomUUID().toString();;
    private String title;
    private String author;
    private String genre;
    private int pages;

    public Book(String title, String author, String genre, int pages) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", pages=" + pages +
                '}';
    }
}