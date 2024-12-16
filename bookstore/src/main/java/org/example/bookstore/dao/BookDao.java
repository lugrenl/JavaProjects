package org.example.bookstore.dao;

import org.example.bookstore.models.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll();
    Book save(Book book);
    Book getById(String bookId);
    Book update(Book book);
    void delete(String bookId);
    void deleteAll();
}
