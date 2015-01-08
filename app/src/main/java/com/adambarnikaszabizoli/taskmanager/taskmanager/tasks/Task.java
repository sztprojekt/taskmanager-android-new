package com.adambarnikaszabizoli.taskmanager.taskmanager.tasks;

import java.text.DateFormat;

public class Task {

    private int id;
    private String name;
    private boolean status;
    private DateFormat dueDate;
    private DateFormat completedAt;
    private String dueDateString;

    public Task() {

    }

    public Task(String name){
        this.name = name;
    }

    public Task(int id, String name, boolean status, DateFormat dueDate, DateFormat completedAt) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.dueDate = dueDate;
        this.completedAt = completedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setDueDate(DateFormat dueDate) {
        this.dueDate = dueDate;
    }

    public void setCompletedAt(DateFormat completedAt) {
        this.completedAt = completedAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }

    public DateFormat getDueDate() {
        return dueDate;
    }

    public DateFormat getCompletedAt() {
        return completedAt;
    }

    public void setDueDateString(String dueDateString) {
        this.dueDateString = dueDateString;
    }

    public String getDueDateString() {
        return dueDateString;
    }

    public String toString() {
        return "ID : " + id + " name: " + name;
    }
}
