package com.example.kingj.todo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Task.class},version = 1,exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    abstract TaskDao getTaskDao();

}
