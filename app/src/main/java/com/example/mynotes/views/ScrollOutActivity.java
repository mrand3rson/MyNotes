package com.example.mynotes.views;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import com.example.mynotes.R;

/**
 * Created by Andrei on 27.01.2018.
 */

abstract class ScrollOutActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= 21) {
            overridePendingTransition(R.anim.enter_left_in, R.anim.exit_right_out);
        }
    }
}
