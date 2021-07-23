package com.tnr;

public class Reminder_card_data {

    private long id;
    private String title;
    private String description;
    private String date;
    private String time;

    public Reminder_card_data(long id1, String title1, String description1,String date1, String time1)
    {
        this.id = id1;
        this.title = title1;
        this.description = description1;
        this.date = date1;
        this.time = time1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
