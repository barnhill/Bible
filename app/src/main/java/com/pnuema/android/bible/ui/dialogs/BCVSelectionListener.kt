package com.pnuema.android.bible.ui.dialogs

interface BCVSelectionListener {
    fun onBookSelected(book: Int)
    fun onChapterSelected(chapter: Int)
    fun onVerseSelected(verse: Int)
    fun refresh()
}