package com.pnuema.simplebible.ui.dialogs;

import com.pnuema.simplebible.data.bibles.org.Books;
import com.pnuema.simplebible.data.bibles.org.Chapters;
import com.pnuema.simplebible.data.bibles.org.Verses;

public interface NotifySelectionCompleted {
    void onSelectionComplete(Books.Book book, Chapters.Chapter chapter, Verses.Verse verse);
}
