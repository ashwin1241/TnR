package com.tnr;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Lists_Card_Data implements Serializable {

    @PrimaryKey
    private long id;
    @ColumnInfo(name = "title")
    private String title;

    public Lists_Card_Data(long id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
