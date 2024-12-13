package org.example.dao;

import org.example.entity.Task;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class TaskDao {
    private final DataSource dataSource;

    public TaskDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Task save(Task task) {
        /*
        get connection
        create statement
        set params
        execute
        get id
        set id
        */
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT task_id, title, finished, created_date FROM task ORDER BY task_id";
        return getTasks(tasks, sql);
    }

    public int deleteAll() {
        String sql = "WITH deleted AS (DELETE FROM task RETURNING *) SELECT COUNT(*) FROM deleted";
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Failed to delete all tasks");
    }

    public Task getById(Integer id) {
        String sql = "SELECT task_id, title, finished, created_date FROM task WHERE task_id = ?";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToTask(resultSet);
            }
            throw new RuntimeException("Task with id " + id + " not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> findAllNotFinished() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT task_id, title, finished, created_date FROM task WHERE finished = false";
        return getTasks(tasks, sql);
    }

    public List<Task> findNewestTasks(Integer numberOfNewestTasks) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT task_id, title, finished, created_date FROM task ORDER BY created_date DESC limit ?";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, numberOfNewestTasks);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(mapToTask(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }

    public Task finishTask(Task task) {
        String sql = "UPDATE task SET finished = true WHERE task_id = ?";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, task.getId());
            statement.executeUpdate();
            task.setFinished(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM task WHERE task_id = ?";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Task mapToTask(ResultSet resultSet) throws SQLException {
        Task task = new Task(
                resultSet.getString(2),
                resultSet.getBoolean(3),
                resultSet.getTimestamp(4).toLocalDateTime()
        );
        task.setId(resultSet.getInt(1));
        return task;
    }

    private List<Task> getTasks(List<Task> tasks, String sql) {
        try(
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while(resultSet.next()) {
                tasks.add(mapToTask(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tasks;
    }
}
