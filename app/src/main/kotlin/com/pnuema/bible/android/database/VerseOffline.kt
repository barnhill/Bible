package com.pnuema.bible.android.database

import androidx.room.Entity
import com.pnuema.bible.android.data.firefly.Verse
import com.pnuema.bible.android.database.VerseOffline.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME, primaryKeys = ["version", "book", "chapter", "verse"])
data class VerseOffline(
    var version: String,
    var book: Int,
    var chapter: Int,
    var verse: Int,
    var verseText: String
) {
    companion object {
        const val TABLE_NAME = "offlineVerses"
        const val TABLE_NAME_FTS = TABLE_NAME + "_fts"
    }
    fun convertToVerse(): Verse = Verse(book, chapter, verse, verseText)
}