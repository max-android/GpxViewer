package com.primerexample.gpxviewer.ui;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Максим on 01.12.2017.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
