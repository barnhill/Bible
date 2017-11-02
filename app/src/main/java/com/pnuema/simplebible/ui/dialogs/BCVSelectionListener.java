package com.pnuema.simplebible.ui.dialogs;

import com.pnuema.simplebible.data.IBook;
import com.pnuema.simplebible.data.IChapter;
import com.pnuema.simplebible.data.IVerse;

public interface BCVSelectionListener {
    void onBookSelected(IBook book);
    void onChapterSelected(IChapter chapter);
    void onVerseSelected(IVerse verse);
    void refresh();
}