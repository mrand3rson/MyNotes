package com.example.mynotes.db;

import com.example.mynotes.models.Note;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Andrei on 27.01.2018.
 */

public class NoteDAO extends BaseDaoImpl<Note, Integer> {

    NoteDAO(ConnectionSource connectionSource,
                      Class<Note> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public synchronized List<Note> getAllNotes() throws SQLException{
        return this.queryForAll();
    }

    public synchronized int editNote(Note note) throws SQLException {
        return this.update(note);
    }

    public synchronized Note getNoteById(int id) throws SQLException{
        return this.queryForId(id);
    }
}