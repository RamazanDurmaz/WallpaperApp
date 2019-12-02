package com.ramazandurmaz.walpaper.common;

import android.app.Application;
import android.content.Context;

import com.ramazandurmaz.walpaper.model.pref.MyPref;

public class MyApp extends Application {
    private static MyPref preferences;
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static MyPref getPreferences(){
        if (preferences == null)
            preferences = new MyPref(appContext);
        return preferences;
    }
}
