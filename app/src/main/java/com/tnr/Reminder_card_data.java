package com.tnr;

public class Reminder_card_data {

    private String title;
    private int index;
    private String data;

    public Reminder_card_data(String title1, int index1)
    {
        this.title = title1;
        this.index = index1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
