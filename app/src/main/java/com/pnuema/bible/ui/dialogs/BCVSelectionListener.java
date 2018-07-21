package com.pnuema.bible.ui.dialogs;

import com.pnuema.bible.data.IBook;
import com.pnuema.bible.data.IChapter;
import com.pnuema.bible.data.IVerse;

public interface BCVSelectionListener {
    void onBookSelected(IBook book);
    void onChapterSelected(IChapter chapter);
    void onVerseSelected(IVerse verse);
    void refresh();
}