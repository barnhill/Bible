package com.pnuema.bible.retrievers;

import java.util.Observable;

public abstract class BaseRetriever extends Observable {
    BaseRetriever() {
    }

    String getTag() {
        return getClass().getSimpleName();
    }

    public abstract void savePrefs();

    public abstract void readPrefs();

    public abstract void getVersions();

    public abstract void getBooks();

    public abstract void getChapters(String book);

    public abstract void getVerseCount(String version, String book, String chapter);

    public abstract void getVerses(String version, String book, String chapter);
}
