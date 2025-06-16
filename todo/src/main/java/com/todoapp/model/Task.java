package com.todoapp.model;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class Task {

    private String id;
    private String title;
    private String description;
    private boolean completed;
    private String createdAt;
    private String updatedAt;
    

   
    public Task(String title, String description) {
        this();
        this.title = title;
        this.description = description;
        this.completed = false;
    }
    public Task() {
        
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    
    

}