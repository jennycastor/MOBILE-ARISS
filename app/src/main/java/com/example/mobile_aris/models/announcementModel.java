package com.example.mobile_aris.models;

public class announcementModel {

    String date, desc, title,author;

    public announcementModel() {
    }

    public announcementModel(String date, String desc, String title,String author) {
        this.date = date;
        this.desc = desc;
        this.title = title;
        this.author=author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
