package com.pnuema.android.bible.statics;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pnuema.android.bible.retrievers.BaseRetriever;
import com.pnuema.android.bible.retrievers.FireflyRetriever;

public final class CurrentSelected {
    private static String mVersion = "kjv";
    private static Integer mBook;
    private static Integer mChapter;
    private static Integer mVerse;
    private static BaseRetriever mRetriever = new FireflyRetriever();

    private CurrentSelected() {
    }

    public static Integer getVerse() {
        if (mVerse == null) {
            setVerse(1);
        }
        return mVerse;
    }

    public static void setVerse(final Integer verse) {
        mVerse = verse;
    }

    public static void clearVerse() {
        mVerse = null;
    }

    public static Integer getChapter() {
        if (mChapter == null) {
            setChapter(1);
        }
        return mChapter;
    }

    public static void setChapter(final Integer chapter) {
        mChapter = chapter;
    }

    public static void clearChapter() {
        mChapter = null;
    }

    public static Integer getBook() {
        if (mBook == null) {
            setBook(1);
        }
        return mBook;
    }

    public static void setBook(final Integer book) {
        mBook = book;
    }

    public static String getVersion() {
        return mVersion;
    }

    public static void setVersion(final String version) {
        mVersion = version;
    }

    public static void readPreferences() {
        mRetriever.readPrefs();
    }

    public static void savePreferences() {
        mRetriever.savePrefs();
    }

    public static void savePref(final String prefName, final String prefValue) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }
}
