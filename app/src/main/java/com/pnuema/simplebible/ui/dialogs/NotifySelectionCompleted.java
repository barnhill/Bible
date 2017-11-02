package com.pnuema.simplebible.ui.dialogs;

import com.pnuema.simplebible.data.IBook;
import com.pnuema.simplebible.data.IChapter;
import com.pnuema.simplebible.data.IVerse;

public interface NotifySelectionCompleted {
    void onSelectionComplete(IBook book, IChapter chapter, IVerse verse);
}
