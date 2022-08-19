package com.example.todo.Model;

public class toDoModel {

    private int id, status; //id attribute in the DB, status 0 for NOT done and 1 for Done
    private String task; //actual text written in the App

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
