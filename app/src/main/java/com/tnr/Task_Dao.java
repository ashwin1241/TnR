package com.tnr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Task_Dao {
    @Query("SELECT * FROM Tasks_Card_Data")
    List<Tasks_Card_Data> getAll();

    @Query("SELECT * FROM Tasks_Card_Data WHERE id LIKE :card_id")
    Tasks_Card_Data getTask(long card_id);

    @Insert
    void insert(Tasks_Card_Data card_data);

    @Delete
    void delete(Tasks_Card_Data card_data);

    @Update
    void update(Tasks_Card_Data card_data);
}
