package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dao.TaskDao;
import org.example.models.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class TasksController {

    private final TaskDao taskDao;

    @GetMapping("/")
    public String taskList(Model model) {
        model.addAttribute("tasks", taskDao.findAll());
        return "tasks-list";
    }

    @GetMapping("/create-form")
    public String createForm() { return "create-form"; }

    @PostMapping("/create")
    public String create(@RequestParam("title") String title) {
        taskDao.save(new Task(title, false));
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        taskDao.deleteById(Integer.parseInt(id));
        return "redirect:/";
    }

    @GetMapping("/finish/{id}")
    public String finish(@PathVariable("id") String id) {
        Task taskToFinish = taskDao.getById(Integer.parseInt(id));
        taskDao.finishTask(taskToFinish);
        return "redirect:/";
    }
}