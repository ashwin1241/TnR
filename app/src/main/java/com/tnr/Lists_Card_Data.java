package com.tnr;

public class Lists_Card_Data {

    private long id;
    private String title;

    public Lists_Card_Data(long id1, String title1)
    {
        this.id = id1;
        this.title = title1;
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
