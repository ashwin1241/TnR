package com.tnr;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Inner_List_Dao {

    @Query("SELECT * FROM Inner_List_Card_Data WHERE parent_id LIKE :parent_id")
    List<Inner_List_Card_Data> getAll(long parent_id);

    @Query("SELECT * FROM Inner_List_Card_Data WHERE id LIKE :card_id")
    Inner_List_Card_Data getList(long card_id);

    @Insert
    void insert(Inner_List_Card_Data card_data);

    @Delete
    void delete(Inner_List_Card_Data card_data);

    @Update
    void update(Inner_List_Card_Data card_data);

}
