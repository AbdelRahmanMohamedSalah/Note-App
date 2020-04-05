package com.example.abdelrahman.note;

/**
 * Created by Abdel Rahman on 29-Mar-18.
 */

public class item {


    String title;
    String note;
    String date;
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public item(String title, String note, String date,String key) {
        this.title = title;
        this.note = note;
        this.date = date;
        this.key=key;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
