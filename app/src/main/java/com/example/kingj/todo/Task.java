package com.example.kingj.todo;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Task {


    private String taskTitle;
    private String taskDate;
    private String taskTime;

    @PrimaryKey(autoGenerate = true)
   private int id;

    public Task(String taskDate,String taskTitle,String taskTime)
    {
        this.id=id;
        this.taskDate=taskDate;
        this.taskTime=taskTime;
        this.taskTitle=taskTitle;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
