package org.example.bookstore.dao;

import org.example.bookstore.models.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"jdbcUrl=jdbc:h2:mem:db"})
class BookDaoImplTest {

    @Autowired
    private BookDao bookDao;

    @Test
    void findAll() {
    }

    @Test
    void save() {
    }

    @Test
    void getById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
        bookDao.save(new Book("title", "author", "genre", 1));
        assertThat(bookDao.findAll()).isNotEmpty();
        bookDao.deleteAll();
        assertThat(bookDao.findAll()).isEmpty();
    }
}