package org.example.bookstore.models;

import jakarta.validation.constraints.*;
import lombok.Setter;
import lombok.Getter;

import java.util.UUID;

@Getter
@Setter
public class Book {
    @NotNull(message = "Id should not be null")
    private String id = UUID.randomUUID().toString();

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 1, max = 100, message = "Title should be between 1 and 100 characters")
    private String title;

    @NotEmpty(message = "Author: Firstname Lastname not be empty")
    @Size(min = 1, max = 100, message = "Author: Firstname Lastname be between 1 and 100 characters")
    private String author;

    @NotEmpty(message = "Genre should not be empty")
    @Size(min = 1, max = 100, message = "Genre should be between 1 and 100 characters")
    private String genre;

    @NotNull(message = "Pages should not be null")
    @Min(value = 1, message = "Pages should be at least 1")
    @Max(value = 10000, message = "Pages should be at most 10000")
    private Integer pages;

    public Book(String title, String author, String genre, Integer pages) {
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