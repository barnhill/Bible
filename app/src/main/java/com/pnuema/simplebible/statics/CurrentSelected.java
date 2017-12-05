package com.pnuema.simplebible.statics;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.pnuema.simplebible.data.IBook;
import com.pnuema.simplebible.data.IChapter;
import com.pnuema.simplebible.data.IVerse;
import com.pnuema.simplebible.data.IVersion;
import com.pnuema.simplebible.retrievers.BaseRetriever;

import java.lang.reflect.InvocationTargetException;

public final class CurrentSelected {
    private static IVersion mVersion;
    private static IBook mBook;
    private static IChapter mChapter;
    private static IVerse mVerse;
    private static BaseRetriever mRetriever;

    private CurrentSelected() {
    }

    public static BaseRetriever getRetriever() {
        String type = PreferenceManager.getDefaultSharedPreferences(App.getContext()).getString(Constants.KEY_RETRIEVER_TYPE, Constants.DEFAULT_RETRIEVER_TYPE);

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
    public static void setRetriever(Class clazz) {
        savePref(Constants.KEY_RETRIEVER_TYPE, clazz.getName());
    }

    @Nullable
    public static IVerse getVerse() {
        return mVerse;
    }

    public static void setVerse(IVerse mVerse) {
        CurrentSelected.mVerse = mVerse;
    }

    public static void clearVerse() {
        CurrentSelected.mVerse = null;
    }

    public static IChapter getChapter() {
        return mChapter;
    }

    public static void setChapter(IChapter mChapter) {
        CurrentSelected.mChapter = mChapter;
    }

    public static void clearChapter() {
        CurrentSelected.mChapter = null;
    }

    public static IBook getBook() {
        return mBook;
    }

    public static void setBook(IBook mBook) {
        CurrentSelected.mBook = mBook;
    }

    public static void clearBook() {
        CurrentSelected.mBook = null;
    }

    public static IVersion getVersion() {
        return mVersion;
    }

    public static void setVersion(IVersion mVersion) {
        CurrentSelected.mVersion = mVersion;
    }

    public static void readPreferences() {
        getRetriever().readPrefs();
    }

    public static void savePreferences() {
        getRetriever().savePrefs();
    }

    public static void savePref(String prefName, String prefValue) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }
}
