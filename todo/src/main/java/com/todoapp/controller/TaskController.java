package com.todoapp.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.model.Task;
import com.todoapp.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public String createTask(@RequestBody Task task)
    {
        service.createTask(task);
        return "Task created successfully with ID: " + task.getId();
    }
    @GetMapping
    public List<Task> getTasks()
    {
        return service.getTasks();
    }
    @GetMapping("/{id}")
    public Task getTask(@PathVariable String id)
    {
        return service.getTask(id);
    }
    @PutMapping("/{id}")
    public String updateTask(@PathVariable String id ,@RequestBody Task task)
    {
        
        Task existingtask = service.getTask(id);
        if(existingtask == null)
        {
            return("No task found");
        }
        else
        {
            service.updateTask(id, task);
            return ("Task updated for ID : " +id);
        }
    }
}
