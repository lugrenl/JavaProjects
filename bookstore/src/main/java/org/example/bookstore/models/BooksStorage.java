package org.example.bookstore.models;

import java.util.ArrayList;
import java.util.List;

public class BooksStorage {
    private static final List<Book> booksList = new ArrayList<>();

    static {
        booksList.add(
                new Book(
                        "Инструментарий хакера",
                        "Бабин С.",
                        "Программирование")
        );
        booksList.add(
                new Book(
                        "Академия и Империя (Основание)",
                        "Айзек Азимов",
                        "Научная фантастика")
        );

        booksList.add(
                new Book(
                        "Байки из грота. 50 историй из жизни древних людей",
                        "Станислав Дробышевский",
                        "Научно-популярная литература")
        );

    }

    public static List<Book> getBooks() {
        return booksList;
    }
}