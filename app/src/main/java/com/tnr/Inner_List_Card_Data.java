package com.tnr;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class Inner_List_Card_Data implements Serializable {
    @PrimaryKey
    private long id;
    @ColumnInfo(name = "parent_id")
    private long parent_id;
    @ColumnInfo(name = "title")
    private String title;

    public Inner_List_Card_Data(long id, long parent_id, String title)
    {
        this.id = id;
        this.parent_id = parent_id;
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

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }
}
