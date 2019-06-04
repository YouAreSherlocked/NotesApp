package com.example.notesapp.dal;

import com.example.notesapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesDao {
    public static List<Note> getAll() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note("Note 1", "Das ist die erste Notiz. Juhuu!", true));
        notes.add(new Note("Note 2", "Das ist die zweite Notiz. Juhuu!", false));
        notes.add(new Note("Note 3", "Das ist die dritte Notiz. Juhuu!", false));
        notes.add(new Note("Note 4", "Das ist die vierte Notiz. Juhuu!", false));
        notes.add(new Note("Note 5", "Das ist die fünfte Notiz. Juhuu!", false));
        return notes;
    }
}
