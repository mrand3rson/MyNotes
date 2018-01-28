package com.example.mynotes.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mynotes.R;
import com.example.mynotes.presenters.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rView)
    RecyclerView rView;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    MainPresenter mainPresenter = new MainPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mainPresenter.loadDataFromDatabase();
        mainPresenter.fillRecyclerView(rView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_note: {
                mainPresenter.openAddNoteActivity(this);
                overridePendingTransition(R.anim.enter_right_in, R.anim.exit_left_out);
                break;
            }
        }
        return true;
    }
}
