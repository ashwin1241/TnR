package com.tnr;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Tasks_Card_Data.class,Lists_Card_Data.class,Inner_List_Card_Data.class,Reminders_Card_Data.class}, version = 1)
public abstract class App_Databse extends RoomDatabase {
    public abstract Task_Dao task_dao();
    public abstract List_Dao list_dao();
    public abstract Inner_List_Dao inner_list_dao();
}
