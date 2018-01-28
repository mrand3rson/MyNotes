package com.example.mynotes.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mynotes.R;
import com.example.mynotes.models.Note;
import com.example.mynotes.presenters.EditPresenter;

import java.sql.SQLException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Andrei on 27.01.2018.
 */

public class EditNoteActivity extends AppCompatActivity {

    @BindView(R.id.edit_title)
    EditText eTitle;

    @BindView(R.id.edit_description)
    EditText eDescription;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    EditPresenter editPresenter = new EditPresenter(this);
    private boolean preventSavingOnPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        setUi();
        editPresenter.unpackIntent();
    }

    @Override
    public void onBackPressed() {
        preventSavingOnPause = true;
        if (saveNote())
            super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftKeyboard(eDescription);
        if (!preventSavingOnPause){
            saveNote();
        }
    }

    private void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean saveNote() {
        boolean success = false;
        try {
            success = editPresenter.saveItemToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    private void setUi() {
        ButterKnife.bind(this);
        ButterKnife.bind(toolbar);
        setSupportActionBar(toolbar);
        addBackButton();
    }

    private void addBackButton() {
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(v -> {
            preventSavingOnPause = true;
            finish();
            overridePendingTransition(R.anim.enter_left_in, R.anim.exit_right_out);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_note: {
                onBackPressed();
                break;
            }
        }
        return true;
    }

    public void updateUi(String title, String description) {
        if (title != null) {
            eTitle.setText(title);
        }
        if (description != null) {
            eDescription.setText(description);
        }
    }

    private boolean validateFields() {
        if (eTitle.getText().toString().isEmpty()) {
            eTitle.requestFocus();
            eTitle.setError("This field is required");
            return false;
        } else {
            return true;
        }
    }

    public Note makeNoteInstance(int id) {
        if (validateFields()) {
            Calendar c = Calendar.getInstance();
            return new Note(id, eTitle.getText().toString(),
                    eDescription.getText().toString(),
                    c.getTime()
            );
        } else {
            return null;
        }
    }

    public Note makeNoteInstance() {
        if (validateFields()) {
            Calendar c = Calendar.getInstance();
            return new Note(eTitle.getText().toString(),
                    eDescription.getText().toString(),
                    c.getTime()
            );
        } else {
            return null;
        }
    }

    public void showMessage(int status) {
        Log.d("debug", String.valueOf(status));
        Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
    }
}
