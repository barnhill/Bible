package com.pnuema.simplebible.statics;

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
}
