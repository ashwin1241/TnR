package com.tnr;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Tasks_Card_Data.class}, version = 1)
public abstract class Tasks_Databse extends RoomDatabase {
    public abstract Task_Dao task_dao();
}
