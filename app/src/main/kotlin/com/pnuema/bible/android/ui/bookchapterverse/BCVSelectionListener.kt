package com.pnuema.bible.android.ui.bookchapterverse

interface BCVSelectionListener {
    fun onBookSelected(book: Int)
    fun onChapterSelected(chapter: Int)
    fun onVerseSelected(verse: Int)
    fun refresh()
}