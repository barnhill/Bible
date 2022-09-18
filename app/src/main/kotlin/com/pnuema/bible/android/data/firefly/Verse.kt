package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.database.VerseOffline

data class Verse(
    val book: Int = 0,
    val chapter: Int = 0,
    val verse: Int = 0,
    val verseText: String
) : IVerse {
    override fun getText(): String = verseText
    override fun getVerseNumber(): Int = verse

    override fun convertToOfflineModel(version: String) = VerseOffline(
        version = version,
        book = book,
        chapter = chapter,
        verse = verse,
        verseText = verseText
    )
}
