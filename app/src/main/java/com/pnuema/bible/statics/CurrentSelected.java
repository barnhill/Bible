package com.pnuema.bible.statics;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pnuema.bible.retrievers.BaseRetriever;
import com.pnuema.bible.retrievers.FireflyRetriever;

public final class CurrentSelected {
    private static String mVersion;
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

    public static void setVerse(final Integer mVerse) {
        CurrentSelected.mVerse = mVerse;
    }

    public static void clearVerse() {
        CurrentSelected.mVerse = null;
    }

    public static Integer getChapter() {
        return mChapter;
    }

    public static void setChapter(final Integer mChapter) {
        CurrentSelected.mChapter = mChapter;
    }

    public static void clearChapter() {
        CurrentSelected.mChapter = null;
    }

    public static Integer getBook() {
        return mBook;
    }

    public static void setBook(final Integer mBook) {
        CurrentSelected.mBook = mBook;
    }

    public static String getVersion() {
        return mVersion;
    }

    public static void setVersion(final String mVersion) {
        CurrentSelected.mVersion = mVersion;
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
