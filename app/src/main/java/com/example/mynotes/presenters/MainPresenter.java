package com.example.mynotes.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mynotes.R;
import com.example.mynotes.custom.NotesAdapter;
import com.example.mynotes.custom.VerticalSpaceItemDecoration;
import com.example.mynotes.db.DatabaseHelper;
import com.example.mynotes.db.HelperFactory;
import com.example.mynotes.db.NoteDAO;
import com.example.mynotes.models.Note;
import com.example.mynotes.views.EditNoteActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 28.01.2018.
 */

public class MainPresenter {
    private final int VERTICAL_SPACE_RECYCLER_VIEW = 16;
    private List<Note> notes;

    public void loadDataFromDatabase() {
        DatabaseHelper helper = HelperFactory.getHelper();
        try {
            NoteDAO noteDAO = helper.getNoteDAO();
            notes = noteDAO.getAllNotes();
            notes = reverseList(notes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillRecyclerView(RecyclerView rView) {
        if (rView.getAdapter() == null) {
            LinearLayoutManager llm = new LinearLayoutManager(rView.getContext());
            rView.setLayoutManager(llm);
            rView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_SPACE_RECYCLER_VIEW));
            rView.setAdapter(new NotesAdapter(rView.getContext(), notes, R.layout.list_item));
        } else {
            rView.swapAdapter(new NotesAdapter(
                    rView.getContext(), notes, R.layout.list_item),
                    false);
        }
    }

    public void openAddNoteActivity(Context context) {
        Intent intent = new Intent(context, EditNoteActivity.class);
        context.startActivity(intent);
    }

    private List<Note> reverseList(List<Note> notes) {
        List<Note> newNotes = new ArrayList<>(notes.size());
        for (int i = notes.size()-1; i >= 0; i--) {
            newNotes.add(notes.get(i));
        }
        return newNotes;
    }
}
