package com.example.mynotes;

import android.app.Application;

import com.example.mynotes.db.HelperFactory;

/**
 * Created by Andrei on 27.01.2018.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
