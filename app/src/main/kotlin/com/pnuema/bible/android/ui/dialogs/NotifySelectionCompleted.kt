package com.pnuema.bible.android.ui.dialogs

interface NotifySelectionCompleted {
    fun onSelectionComplete(book: Int, chapter: Int, verse: Int)
}
