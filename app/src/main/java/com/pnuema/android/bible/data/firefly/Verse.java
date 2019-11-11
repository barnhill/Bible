package com.pnuema.android.bible.data.firefly;

import com.pnuema.android.bible.R;
import com.pnuema.android.bible.data.IVerse;
import com.pnuema.android.bible.statics.App;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

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

    private String getVerseText() {
        return formatHtmlVerse();
    }

    @NonNull
    @Override
    public String getText() {
        return getVerseText();
    }

    @Override
    public int getVerseNumber() {
        return verse;
    }

    private String formatHtmlVerse() {
        verseText = verseText.replace("¶", "");
        return "<font color=\"#" + String.format("#%06x", ContextCompat.getColor(App.getContext(), R.color.secondary_text) & 0xffffff) + "\"><small>" + getVerseNumber() + "&nbsp;&nbsp;</small></font>" + verseText;
    }
}
