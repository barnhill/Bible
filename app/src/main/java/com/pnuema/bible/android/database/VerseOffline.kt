package com.pnuema.bible.android.database

import androidx.room.Entity
import com.pnuema.bible.android.data.firefly.Verse

@Entity(tableName = "offlineVerses", primaryKeys = ["book", "chapter", "verse"])
data class VerseOffline(
    var version: String,
    var book: Int,
    var chapter: Int,
    var verse: Int,
    var verseText: String
) {
    fun convertToVerse(): Verse = Verse(book, chapter, verse, verseText)
}