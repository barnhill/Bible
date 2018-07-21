package com.pnuema.bible.statics;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public final class App extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        Stetho.initializeWithDefaults(this);
    }

    public static Context getContext() {
        return appContext;
    }
}
