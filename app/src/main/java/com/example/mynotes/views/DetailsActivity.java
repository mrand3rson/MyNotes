package com.example.mynotes.views;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.models.Note;
import com.example.mynotes.presenters.DetailsPresenter;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends ScrollOutActivity {

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @BindView(R.id.text_description)
    TextView tDescription;

    @BindView(R.id.text_date)
    TextView tDate;

    DetailsPresenter detailsPresenter = new DetailsPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        detailsPresenter.loadItemFromDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_note: {
                detailsPresenter.openEditNoteActivity();
                overridePendingTransition(R.anim.enter_right_in, R.anim.exit_left_out);
                break;
            }
            case R.id.action_delete_note: {
                detailsPresenter.deleteConfirmation();
                break;
            }
        }
        return true;
    }

    private void setUi() {
        ButterKnife.bind(this);
        ButterKnife.bind(toolbar);
        setSupportActionBar(toolbar);
    }

    public void updateUi(Note note) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.getDefault());

        toolbar.setTitle(note.getTitle());
        tDescription.setText(note.getDescription());
        tDate.setText(sdf.format(note.getDate()));
    }

    public void fadeAway() {
        finish();
        overridePendingTransition(R.anim.enter_left_in, R.anim.exit_right_out);
    }
}
