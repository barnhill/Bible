package com.pnuema.android.bible.ui.dialogs

interface NotifySelectionCompleted {
    fun onSelectionPreloadChapter(book: Int, chapter: Int)
    fun onSelectionComplete(book: Int, chapter: Int, verse: Int)
}
