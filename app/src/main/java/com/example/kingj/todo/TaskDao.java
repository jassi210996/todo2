package com.example.kingj.todo;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void addTasks(Task task);

    @Query("select * from task")
    List<Task> getTasks();

}
