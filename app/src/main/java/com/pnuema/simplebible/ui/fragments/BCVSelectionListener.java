package com.pnuema.simplebible.ui.fragments;

import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.data.Chapters;
import com.pnuema.simplebible.data.Verses;

public interface BCVSelectionListener {
    void onBookSelected(Books.Book book);
    void onChapterSelected(Chapters.Chapter chapter);
    void onVerseSelected(Verses.Verse verse);
    void refresh();
}