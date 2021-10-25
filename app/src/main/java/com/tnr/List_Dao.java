package com.tnr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface List_Dao {

    @Query("SELECT * FROM Lists_Card_Data")
    List<Lists_Card_Data> getAll();

    @Query("SELECT * FROM Lists_Card_Data WHERE id LIKE :card_id")
    Lists_Card_Data getList(long card_id);

    @Query("DELETE FROM Lists_Card_Data")
    void deleteAll();

    @Insert
    void insert(Lists_Card_Data card_data);

    @Delete
    void delete(Lists_Card_Data card_data);

    @Update
    void update(Lists_Card_Data card_data);

}
