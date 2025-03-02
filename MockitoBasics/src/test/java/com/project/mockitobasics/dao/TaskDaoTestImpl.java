package com.project.mockitobasics.dao;

import com.project.mockitobasics.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Profile("test")
@Component
public class TaskDaoTestImpl implements TaskDao {
    private static final List<Task> taskList = new ArrayList<>();

    @Override
    public Task save(Task task) {
        taskList.add(task);
        return getById(task.getId());
    }

    @Override
    public List<Task> findAll() {
        return taskList;
    }

    @Override
    public int deleteAll() {
        int numberOfDeletedTask = taskList.size();
        taskList.clear();
        return numberOfDeletedTask;
    }

    @Override
    public Task getById(Integer id) {
        return taskList.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Task> findAllNotFinished() {
        return taskList.stream().filter(task -> !task.getFinished()).toList();
    }

    @Override
    public List<Task> findNewestTasks(Integer numberOfNewestTasks) {
        return taskList.stream().skip(Math.max(0, taskList.size() - numberOfNewestTasks)).toList();
    }

    @Override
    public Task finishTask(Task task) {
        taskList.stream().filter(t -> t.getId().equals(task.getId())).findFirst().ifPresent(t -> t.setFinished(true));
        return getById(task.getId());
    }

    @Override
    public void deleteById(Integer id) {
        taskList.removeIf(task -> task.getId().equals(id));
    }

    @Override
    public Task update(Task task) {
        taskList.stream().filter(t -> t.getId().equals(task.getId())).findFirst().ifPresent(t -> t.setTitle(task.getTitle()));
        return getById(task.getId());
    }
}
