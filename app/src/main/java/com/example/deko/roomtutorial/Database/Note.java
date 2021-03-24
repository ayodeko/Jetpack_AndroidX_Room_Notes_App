package com.example.deko.roomtutorial.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "note_table")
public class Note {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String description;
    private String title;
    private int priority;

    public Note(String title, String description, int priority) {
        this.description = description;
        this.title = title;
        this.priority = priority;
    }



    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public void setId(int id) {
        this.id = id;
    }
}
