package com.todoapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.todoapp.dao.TaskDao;
import com.todoapp.model.Task;

@Service
public class TaskService {
    private final TaskDao dao;
    public TaskService(TaskDao dao) {
        this.dao = dao;
    }


    public Task createTask(Task task) {
        dao.save(task);
        return task;
    }
    public Task getTask(String id) {
        return dao.getTaskById(id);
    }
    public List<Task> getTasks() {
        return dao.getTasks();
    }
    public Task updateTask(String id, Task task) {
        return dao.updateTask(id, task);
        
    }

}
