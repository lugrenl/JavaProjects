package org.example.dao;

import org.example.entity.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;

public class TaskDao {
    private final DataSource dataSource;

    public TaskDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Task save(Task task) {
        // get connection
        // create statement
        // set params
        // execute
        // get id
        // set id
        String sql = "INSERT INTO task (title, finished, created_date) VALUES (?, ?, ?)";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            statement.setString(1, task.getTitle());
            statement.setBoolean(2, task.getFinished());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(task.getCreatedDate()));
            statement.executeUpdate();

            try(ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    task.setId(resultSet.getInt(1));
                }
            }

        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }

        return task;
    }

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT task_id, title, finished, created_date FROM task ORDER BY task_id";
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {

            while(resultSet.next()) {
                final Task task = new Task(
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getTimestamp(4).toLocalDateTime()
                );
                task.setId(resultSet.getInt(1));
                tasks.add(task);
            }

        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }

        return tasks;
    }

    public int deleteAll() {

        return 1;
    }

    public Task getById(Integer id) {
        return null;
    }

    public List<Task> findAllNotFinished() {
        return Collections.emptyList();
    }

    public List<Task> findNewestTasks(Integer numberOfNewestTasks) {
        return Collections.emptyList();
    }

    public Task finishTask(Task task) {
        return task;
    }

    public void deleteById(Integer id) {

    }
}