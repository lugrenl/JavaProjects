package com.project.mockitobasics.config;

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

    /*
     * Этот метод выполняется при запуске приложения.
     * Инициализирует таблицу в базе данных.
     */
    @Bean
    public CommandLineRunner cmd(DataSource dataSource) {
        return args -> {
            try(InputStream inputStream = this.getClass().getResourceAsStream("/initial.sql")) {
                String sql = new String(Objects.requireNonNull(inputStream).readAllBytes());
                try(Connection connection = dataSource.getConnection()) {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                }
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
