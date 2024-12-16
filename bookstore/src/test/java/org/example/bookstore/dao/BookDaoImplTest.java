package org.example.bookstore.dao;

import org.example.bookstore.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(properties = {"jdbcUrl=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"})
class BookDaoImplTest {

    @Autowired
    private BookDao bookDao;

    @BeforeEach
    public void setUp() {
        bookDao.deleteAll();
    }

    @Test
    void findAllReturnsAllBooks() {
        assertThat(bookDao.findAll()).isEmpty();
        bookDao.save(new Book("title", "author", "genre", 1));
        assertThat(bookDao.findAll()).isNotEmpty();
    }

    @Test
    void saveSavesDataToDbAndReturnsEntityWithId() {
        Book book = bookDao.save(new Book("title", "author", "genre", 1));
        assertThat(book.getId()).isNotBlank();
        assertThat(bookDao.findAll()).extracting("id").containsExactly(book.getId());
    }

    @Test
    void getByIdThrowsRuntimeExceptionIfBookDoesNotExist() {
        assertThatThrownBy(() -> bookDao.getById("1")).isInstanceOf(RuntimeException.class); // TODO
    }

    @Test
    void getByIdReturnsCorrectBook() {
        Book book = bookDao.save(new Book("title", "author", "genre", 1));
        bookDao.save(new Book("title2", "author2", "genre2", 2));

        assertThat(bookDao.getById(book.getId()))
                .isNotNull()
                .extracting("title")
                .isEqualTo(book.getTitle());

    }

    @Test
    void updateUpdatesDataInDb() {
        Book book = bookDao.save(new Book("title", "author", "genre", 1));
        book.setTitle("title2");
        bookDao.update(book);

        assertThat(bookDao.getById(book.getId()).getTitle()).isEqualTo("title2");
    }

    @Test
    void updateThrowsRuntimeExceptionIfBookDoesNotExist() {
        assertThatThrownBy(() -> bookDao.update(new Book("title", "author", "genre", 1))).isInstanceOf(RuntimeException.class); // TODO
    }

    @Test
    void deleteDeletesCorrectData() {
        Book bookToKeep = bookDao.save(new Book("title", "author", "genre", 1));
        Book bookToDelete = bookDao.save(new Book("title2", "author2", "genre2", 2));
        bookDao.delete(bookToDelete.getId());
        assertThat(bookDao.getById(bookToKeep.getId())).isNotNull();
        assertThatThrownBy(() -> bookDao.getById(bookToDelete.getId())).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteAllDeletesAllData() {
        bookDao.save(new Book("title", "author", "genre", 1));
        assertThat(bookDao.findAll()).isNotEmpty();
        bookDao.deleteAll();
        assertThat(bookDao.findAll()).isEmpty();
    }
}