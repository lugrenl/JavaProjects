package com.project.mockitobasics.dao;

import com.project.mockitobasics.exceptions.TaskNotFoundException;
import com.project.mockitobasics.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
public class NamedJdbcDao implements TaskDao {

    private static final String SAVE_TASK = "INSERT INTO task (title, finished, created_date) VALUES (:title, :finished, :created_date)";
    private static final String FIND_ALL_TASK = "SELECT task_id, title, finished, created_date FROM task ORDER BY task_id";
    private static final String UPDATE_TASK = "UPDATE task SET title = :title, finished = :finished, created_date = :created_date WHERE task_id = :task_id";
    private static final String GET_BY_ID_TASK = "SELECT task_id, title, finished, created_date FROM task WHERE task_id = :task_id";
    private static final String FIND_ALL_NOT_FINISHED_TASK = "SELECT task_id, title, finished, created_date FROM task WHERE finished = false";
    private static final String FIND_NEWEST_TASK = "SELECT task_id, title, finished, created_date FROM task ORDER BY created_date DESC limit :numberOfNewestTasks";
    private static final String FINISH_TASK = "UPDATE task SET finished = :finished WHERE task_id = :task_id";
    private static final String DELETE_ALL_TASK = "DELETE FROM task";
    private static final String DELETE_BY_ID_TASK = "DELETE FROM task WHERE task_id = :task_id";

    private static final RowMapper<Task> TASK_ROW_MAPPER =
            (rs, rowNum) -> new Task(
                    rs.getInt("task_id"),
                    rs.getString("title"),
                    rs.getBoolean("finished"),
                    rs.getTimestamp("created_date").toLocalDateTime().truncatedTo(ChronoUnit.MICROS));

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public NamedJdbcDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public Task save(Task task) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var args = new MapSqlParameterSource()
                .addValue("title", task.getTitle())
                .addValue("finished", task.getFinished())
                .addValue("created_date", task.getCreatedDate());

        namedJdbcTemplate.update(SAVE_TASK, args, keyHolder, new String[] { "task_id" });

        if (keyHolder.getKey() == null) {
            throw new TaskNotFoundException("Task not saved: " + task.getId());
        }
        task.setId(keyHolder.getKey().intValue());
        return task;
    }

    @Override
    public List<Task> findAll() {
        return namedJdbcTemplate.query(FIND_ALL_TASK, TASK_ROW_MAPPER);
    }

    @Override
    public int deleteAll() {
        return namedJdbcTemplate.update(DELETE_ALL_TASK, new MapSqlParameterSource());
    }

    @Override
    public Task getById(Integer id) {
        try { return namedJdbcTemplate.queryForObject(GET_BY_ID_TASK, new MapSqlParameterSource("task_id", id), TASK_ROW_MAPPER); } catch (
                EmptyResultDataAccessException e) { return null; }
    }

    @Override
    public List<Task> findAllNotFinished() {
        return namedJdbcTemplate.query(FIND_ALL_NOT_FINISHED_TASK, TASK_ROW_MAPPER);
    }

    @Override
    public List<Task> findNewestTasks(Integer numberOfNewestTasks) {
        return namedJdbcTemplate.query(FIND_NEWEST_TASK, new MapSqlParameterSource("numberOfNewestTasks", numberOfNewestTasks), TASK_ROW_MAPPER);
    }

    @Override
    public Task finishTask(Task task) {
        namedJdbcTemplate.update(FINISH_TASK, new MapSqlParameterSource("finished", true).addValue("task_id", task.getId()));
        task.setFinished(true);
        return task;
    }

    @Override
    public void deleteById(Integer id) {
        namedJdbcTemplate.update(DELETE_BY_ID_TASK, new MapSqlParameterSource("task_id", id));
    }

    @Override
    public Task update(Task task) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        var args = new MapSqlParameterSource()
                .addValue("task_id", task.getId())
                .addValue("title", task.getTitle())
                .addValue("finished", task.getFinished())
                .addValue("created_date", task.getCreatedDate());

        namedJdbcTemplate.update(UPDATE_TASK, args, keyHolder, new String[] { "task_id" });

        if (keyHolder.getKey() == null) {
            throw new TaskNotFoundException("Task not found: " + task.getId());
        }
        return getById(task.getId());
    }
}
