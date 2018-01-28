package com.example.mynotes.presenters;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.mynotes.R;
import com.example.mynotes.db.DatabaseHelper;
import com.example.mynotes.db.HelperFactory;
import com.example.mynotes.db.NoteDAO;
import com.example.mynotes.models.Note;
import com.example.mynotes.views.DetailsActivity;
import com.example.mynotes.views.EditNoteActivity;

import java.sql.SQLException;

/**
 * Created by Andrei on 28.01.2018.
 */

public class DetailsPresenter {
    private final DetailsActivity activity;
    private NoteDAO noteDAO;
    private Note note;

    public DetailsPresenter(DetailsActivity activity) {
        this.activity = activity;

        DatabaseHelper helper = HelperFactory.getHelper();
        try {
            noteDAO = helper.getNoteDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadItemFromDatabase() {
        int id = activity.getIntent().getIntExtra("id", -1);
        if (id == -1) {
            Toast.makeText(activity, "Not found", Toast.LENGTH_LONG).show();
        } else {
            try {
                note = noteDAO.getNoteById(id);
                activity.updateUi(note);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void openEditNoteActivity() {
        Intent intent = new Intent(activity, EditNoteActivity.class);
        intent.putExtra("id", note.getId());
        intent.putExtra("title", note.getTitle());
        intent.putExtra("description", note.getDescription());
        activity.startActivityForResult(intent, 0);
    }

    public void deleteConfirmation() {
        AlertDialog dlg = new AlertDialog.Builder(activity)
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(android.R.string.yes, (dialog, id) -> {
                    try {
                        deleteNote();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton(android.R.string.no, (dialog, id) -> {
                    dialog.dismiss();
                })
                .create();
        dlg.show();
    }

    private void deleteNote() throws SQLException {
        noteDAO.delete(note);
        activity.fadeAway();
    }
}
