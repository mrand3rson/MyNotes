package com.example.mynotes.presenters;

import android.content.Intent;

import com.example.mynotes.db.DatabaseHelper;
import com.example.mynotes.db.HelperFactory;
import com.example.mynotes.db.NoteDAO;
import com.example.mynotes.models.Note;
import com.example.mynotes.views.EditNoteActivity;

import java.sql.SQLException;

/**
 * Created by Andrei on 28.01.2018.
 */

public class EditPresenter {
    private final EditNoteActivity activity;
    private NoteDAO noteDAO;
    private int id;

    public EditPresenter(EditNoteActivity activity) {
        this.activity = activity;

        DatabaseHelper helper = HelperFactory.getHelper();
        try {
            noteDAO = helper.getNoteDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unpackIntent() {
        Intent intent = activity.getIntent();
        id = intent.getIntExtra("id", -1);
        if (id != -1) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            activity.updateUi(title, description);
        }
    }

    public boolean saveItemToDatabase() throws SQLException{
        Note newNote;
        int status = -1;
        if (id != -1) {
            newNote = activity.makeNoteInstance(id);
            if (newNote != null)
                status = noteDAO.editNote(newNote);
        } else {
            newNote = activity.makeNoteInstance();
            if (newNote != null)
                status = noteDAO.create(newNote);
        }

        if (newNote != null) {
            activity.showMessage(status);
            return true;
        }
        return false;
    }
}
