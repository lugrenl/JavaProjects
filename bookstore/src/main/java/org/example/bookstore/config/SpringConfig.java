package org.example.bookstore.config;

import org.example.bookstore.models.Book;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Objects;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringConfig {

    @Bean
    public DataSource h2DataSource(@Value("${jdbcUrl}") String jdbcUrl) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUser("user");
        dataSource.setPassword("password");
        return dataSource;
    }

    @Bean
    public CommandLineRunner cmd(DataSource dataSource) {
        return args -> {
            try(InputStream inputStream = this.getClass().getResourceAsStream("/initial.sql")) {
                String sql = new String(Objects.requireNonNull(inputStream).readAllBytes());
                try(Connection connection = dataSource.getConnection()) {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);

                    String insertSql = "INSERT INTO book (title, author, genre, pages) VALUES (?, ?, ?, ?) ";

                    try(PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                        preparedStatement.setString(1, "The Lord of the Rings");
                        preparedStatement.setString(2, "J.R.R. Tolkien");
                        preparedStatement.setString(3, "Fantasy");
                        preparedStatement.setInt(4, 500);
                        preparedStatement.executeUpdate();
                    }

                    System.out.println("Printing books from db...");
                    ResultSet resultSet = statement.executeQuery("SELECT id, title, author, genre, pages FROM book");
                    while (resultSet.next()) {
                        Book book = new Book(
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5)
                        );
                        book.setId(resultSet.getString(1));
                        System.out.println(book);
                    }
                }

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
