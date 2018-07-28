package com.pnuema.bible.statics;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.pnuema.bible.retrievers.BaseRetriever;

import java.lang.reflect.InvocationTargetException;

public final class CurrentSelected {
    private static String mVersion;
    private static Integer mBook;
    private static Integer mChapter;
    private static Integer mVerse;
    private static BaseRetriever mRetriever;

    private CurrentSelected() {
    }

    public static BaseRetriever getRetriever() {
        final String type = PreferenceManager.getDefaultSharedPreferences(App.getContext()).getString(Constants.KEY_RETRIEVER_TYPE, Constants.DEFAULT_RETRIEVER_TYPE);

        if (mRetriever == null || !mRetriever.getClass().getName().equalsIgnoreCase(type)) {
            try {
                mRetriever = (BaseRetriever) Class.forName(type).getConstructor().newInstance();
                mRetriever.readPrefs();
            } catch (java.lang.InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException ignored) {
                throw new IllegalStateException("Can not create instance of retriever");
            }
        }

        return mRetriever;
    }

    //TODO allow switching retriever sources(DBT, Bibles.org ... etc)
    public static void setRetriever(final Class clazz) {
        savePref(Constants.KEY_RETRIEVER_TYPE, clazz.getName());
    }

    @Nullable
    public static Integer getVerse() {
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

    public static void clearBook() {
        CurrentSelected.mBook = null;
    }

    public static String getVersion() {
        return mVersion;
    }

    public static void setVersion(final String mVersion) {
        CurrentSelected.mVersion = mVersion;
    }

    public static void readPreferences() {
        getRetriever().readPrefs();
    }

    public static void savePreferences() {
        getRetriever().savePrefs();
    }

    public static void savePref(final String prefName, final String prefValue) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        final SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }
}
