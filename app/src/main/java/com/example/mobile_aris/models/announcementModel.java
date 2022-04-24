package com.example.mobile_aris.models;

public class announcementModel {
    String date, desc, title;

    public announcementModel() {
    }

    public announcementModel(String date, String desc, String title) {
        this.date = date;
        this.desc = desc;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
