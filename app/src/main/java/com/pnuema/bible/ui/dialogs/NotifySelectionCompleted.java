package com.pnuema.bible.ui.dialogs;

import com.pnuema.bible.data.IBook;
import com.pnuema.bible.data.IChapter;
import com.pnuema.bible.data.IVerse;

public interface NotifySelectionCompleted {
    void onSelectionPreloadChapter(IBook book, IChapter chapter);
    void onSelectionComplete(IBook book, IChapter chapter, IVerse verse);
}
