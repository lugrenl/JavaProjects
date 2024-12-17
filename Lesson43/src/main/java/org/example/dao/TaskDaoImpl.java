package org.example.dao;

import org.example.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

@Component
public class TaskDaoImpl implements TaskDao {
    private final DataSource dataSource;

    @Autowired
    public TaskDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
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

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT task_id, title, finished, created_date FROM task ORDER BY task_id";
        return getTasks(tasks, sql);
    }

    @Override
    public int deleteAll() {
        String sql = "DELETE FROM task";
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task getById(Integer id) {
        String sql = "SELECT task_id, title, finished, created_date FROM task WHERE task_id = ?";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToTask(resultSet);
                }
            } return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> findAllNotFinished() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT task_id, title, finished, created_date FROM task WHERE finished = false";
        return getTasks(tasks, sql);
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public Task update(Task task) {
        String sql = "UPDATE task SET title = ?, finished = ?, created_date = ? WHERE task_id = ?";
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, task.getTitle());
            statement.setBoolean(2, task.getFinished());
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(task.getCreatedDate()));
            statement.setInt(4, task.getId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    task.setId(resultSet.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
        }

    private Task mapToTask(ResultSet resultSet) throws SQLException {
        Task task = new Task(
                resultSet.getString(2),
                resultSet.getBoolean(3)
        );
        task.setId(resultSet.getInt(1));
        task.setCreatedDate(resultSet.getTimestamp(4).toLocalDateTime());
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
