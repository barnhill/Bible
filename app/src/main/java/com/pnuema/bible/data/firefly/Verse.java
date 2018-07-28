package com.pnuema.bible.data.firefly;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.pnuema.bible.R;
import com.pnuema.bible.data.IVerse;
import com.pnuema.bible.statics.App;

public class Verse implements IVerse {
    private int book;
    private int chapter;
    private int verse;
    private String verseText;

    public int getBook() {
        return book;
    }

    public void setBook(final int book) {
        this.book = book;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(final int chapter) {
        this.chapter = chapter;
    }

    public int getVerse() {
        return verse;
    }

    public void setVerse(final int verse) {
        this.verse = verse;
    }

    public String getVerseText() {
        return formatHtmlVerse();
    }

    public void setVerseText(final String verseText) {
        this.verseText = verseText;
    }

    @Override
    public CharSequence getText(final Context context) {
        return null;
    }

    @Override
    public int getVerseNumber() {
        return verse;
    }

    private String formatHtmlVerse() {
        return "<font color=\"#" + String.format("#%06x", ContextCompat.getColor(App.getContext(), R.color.secondary_text) & 0xffffff) + "\"><small>" + getVerseNumber() + "&nbsp;&nbsp;</small></font>" + verseText;
    }
}
