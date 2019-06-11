package com.example.notesapp.model;

import java.util.Date;

public class Note {
    private String id;
    private String title;
    private String content;
    private boolean isFavourite;
    private Date createdAt;

    public Note(String id, String title, String content, Boolean isFavourite) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isFavourite = isFavourite;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
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
