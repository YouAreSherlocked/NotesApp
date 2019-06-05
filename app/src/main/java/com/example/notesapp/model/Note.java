package com.example.notesapp.model;

import java.util.Date;

public class Note {
    private int id;
    private String title;
    private String content;
    private Boolean isFavourite = false;
    private Date createdAt;

    public Note(String title, String content, Boolean isFavourite) {
        this.title = title;
        this.content = content;
        this.isFavourite = isFavourite;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public Date getDate() {
        return createdAt;
    }

    public String toString() {
        return title;
    }


}
