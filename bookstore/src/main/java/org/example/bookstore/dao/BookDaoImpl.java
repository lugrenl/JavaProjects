package org.example.bookstore.dao;

import org.example.bookstore.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

@Component
public class BookDaoImpl implements BookDao {

    private final DataSource dataSource;

    @Autowired
    public BookDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAll() {
        final String selectSql = "SELECT id, title, author, genre, pages FROM book";
        List <Book> books = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectSql)
        ) {
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)
                );
                book.setId(resultSet.getString(1));
                books.add(book);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Book save(Book book) {
        String insertSql = "INSERT INTO book (title, author, genre, pages) VALUES (?, ?, ?, ?)";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        ) {
            mapToBd(book, preparedStatement);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    @Override
    public Book getById(String bookId) {
        String selectSql = "SELECT id, title, author, genre, pages FROM book WHERE id = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSql)
        ) {
            preparedStatement.setString(1, bookId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = new Book(
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getInt(5)
                    );
                    book.setId(resultSet.getString(1));
                    return book;
                } else {
                    throw new RuntimeException("Book not found" + bookId);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book update(Book book) {
        String updateSql = "UPDATE book SET title = ?, author = ?, genre = ?, pages = ? WHERE id = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(updateSql)
        ) {
            preparedStatement.setString(5, book.getId());
            mapToBd(book, preparedStatement);
        }

        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    private void mapToBd(Book book, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, book.getTitle());
        preparedStatement.setString(2, book.getAuthor());
        preparedStatement.setString(3, book.getGenre());
        preparedStatement.setInt(4, book.getPages());
        preparedStatement.executeUpdate();
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getString(1));
            }
        }
    }


    @Override
    public void delete(String bookId) {
        String deleteSql = "DELETE FROM book WHERE id = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)
        ) {
            preparedStatement.setString(1, bookId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String deleteSQL = "TRUNCATE TABLE book";
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(deleteSQL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
