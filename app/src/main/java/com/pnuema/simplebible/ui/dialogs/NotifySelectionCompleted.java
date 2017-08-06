package com.pnuema.simplebible.ui.dialogs;

import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.data.Chapters;
import com.pnuema.simplebible.data.Verses;

public interface NotifySelectionCompleted {
    void onSelectionComplete(Books.Book book, Chapters.Chapter chapter, Verses.Verse verse);
}
