package com.pnuema.simplebible.statics;

import android.app.Application;

import com.facebook.stetho.Stetho;

public final class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
