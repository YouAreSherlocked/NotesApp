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

    public String toString() {
        return title;
    }


}
