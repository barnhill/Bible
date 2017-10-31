package com.pnuema.simplebible.ui.dialogs;

import com.pnuema.simplebible.data.bibles.org.Books;
import com.pnuema.simplebible.data.bibles.org.Chapters;
import com.pnuema.simplebible.data.bibles.org.Verses;

public interface BCVSelectionListener {
    void onBookSelected(Books.Book book);
    void onChapterSelected(Chapters.Chapter chapter);
    void onVerseSelected(Verses.Verse verse);
    void refresh();
}