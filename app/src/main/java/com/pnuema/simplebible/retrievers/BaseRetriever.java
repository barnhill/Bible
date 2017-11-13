package com.pnuema.simplebible.retrievers;

import android.content.Context;

import java.util.Observable;

public abstract class BaseRetriever extends Observable {
    BaseRetriever() {
    }

    public abstract void getVersions(Context context);

    public abstract void getBooks(Context context);

    public abstract void getChapters(Context context, String book);

    public abstract void getVerses(Context context, String version, String book, String chapter);
}
