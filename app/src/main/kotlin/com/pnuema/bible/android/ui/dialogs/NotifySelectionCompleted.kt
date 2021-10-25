package com.pnuema.bible.android.ui.dialogs

interface NotifySelectionCompleted {
    fun onSelectionPreloadChapter(book: Int, chapter: Int)
    fun onSelectionComplete(book: Int, chapter: Int, verse: Int)
}
