package org.example.config;

import org.example.models.Task;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    /*
     * Этот метод выполняется при запуске приложения.
     * Добавляет тестовую задачу в базу данных.
     */
    @Bean
    public CommandLineRunner cmd(DataSource dataSource) {
        return args -> {
            try(InputStream inputStream = this.getClass().getResourceAsStream("/initial.sql")) {
                String sql = new String(Objects.requireNonNull(inputStream).readAllBytes());
                try(Connection connection = dataSource.getConnection()) {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);

                    String insertSql = "INSERT INTO task (title, finished, created_date) VALUES (?, ?, ?) ";

                    try(PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                        preparedStatement.setString(1, "Test task");
                        preparedStatement.setBoolean(2, false);
                        preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)));
                        preparedStatement.executeUpdate();
                    }

                    System.out.println("Printing tasks from db...");
                    ResultSet resultSet = statement.executeQuery("SELECT task_id, title, finished, created_date FROM task");
                    while (resultSet.next()) {
                        Task task = new Task(
                                resultSet.getString(2),
                                resultSet.getBoolean(3)
                        );
                        task.setId(resultSet.getInt(1));
                        task.setCreatedDate(resultSet.getTimestamp(4).toLocalDateTime());
                        System.out.println(task);
                    }
                }

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
