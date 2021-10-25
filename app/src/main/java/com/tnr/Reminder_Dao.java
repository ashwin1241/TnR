package com.tnr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Reminder_Dao {

    @Query("SELECT * FROM Reminders_Card_Data")
    List<Reminders_Card_Data> getAll();

    @Query("SELECT * FROM Reminders_Card_Data WHERE id LIKE :card_id")
    Reminders_Card_Data getReminder(long card_id);

    @Insert
    void insert(Reminders_Card_Data card_data);

    @Delete
    void delete(Reminders_Card_Data card_data);

    @Update
    void update(Reminders_Card_Data card_data);

}
