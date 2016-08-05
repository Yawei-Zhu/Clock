package com.adreamer.clock.app;

import android.app.Application;

/**
 * Created by A Dreamer on 2016/8/2.
 */
public class AlarmApplication extends Application {

    private static Application mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static Application getApp() {
        return mApp;
    }
}
