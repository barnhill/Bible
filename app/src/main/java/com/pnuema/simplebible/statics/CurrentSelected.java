package com.pnuema.simplebible.statics;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.data.Chapters;
import com.pnuema.simplebible.data.Verses;
import com.pnuema.simplebible.data.Versions;

public final class CurrentSelected {
    private static Versions.Version mVersion;
    private static Books.Book mBook;
    private static Chapters.Chapter mChapter;
    private static Verses.Verse mVerse;

    public static Verses.Verse getVerse() {
        return mVerse;
    }

    public static void setVerse(Verses.Verse mVerse) {
        CurrentSelected.mVerse = mVerse;
    }

    public static void clearVerse() {
        CurrentSelected.mVerse = null;
    }

    public static Chapters.Chapter getChapter() {
        return mChapter;
    }

    public static void setChapter(Chapters.Chapter mChapter) {
        CurrentSelected.mChapter = mChapter;
    }

    public static void clearChapter() {
        CurrentSelected.mChapter = null;
    }

    public static Books.Book getBook() {
        return mBook;
    }

    public static void setBook(Books.Book mBook) {
        CurrentSelected.mBook = mBook;
    }

    public static void clearBook() {
        CurrentSelected.mBook = null;
    }

    public static Versions.Version getVersion() {
        return mVersion;
    }

    public static void setVersion(Versions.Version mVersion) {
        CurrentSelected.mVersion = mVersion;
    }

    public static void readPreferences(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);

        CurrentSelected.setVersion(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSION, null), Versions.Version.class));
        CurrentSelected.setBook(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_BOOK, null), Books.Book.class));
        CurrentSelected.setChapter(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_CHAPTER, null), Chapters.Chapter.class));
        CurrentSelected.setVerse(new Gson().fromJson(sharedPref.getString(Constants.KEY_SELECTED_VERSE, null), Verses.Verse.class));
    }

    public static void savePreferences(Activity activity) {
        savePref(activity, Constants.KEY_SELECTED_VERSION, new Gson().toJson(CurrentSelected.getVersion()));
        savePref(activity, Constants.KEY_SELECTED_BOOK, new Gson().toJson(CurrentSelected.getBook()));
        savePref(activity, Constants.KEY_SELECTED_CHAPTER, new Gson().toJson(CurrentSelected.getChapter()));
        savePref(activity, Constants.KEY_SELECTED_VERSE, new Gson().toJson(CurrentSelected.getVerse()));
    }

    private static void savePref(Activity activity, String prefName, String prefValue) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }
}
