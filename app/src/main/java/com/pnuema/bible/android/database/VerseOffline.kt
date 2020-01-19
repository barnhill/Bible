package com.pnuema.bible.android.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pnuema.bible.android.data.firefly.Verse

@Entity(tableName = "offlineVerses")
data class VerseOffline(var version: String,
                        var book: Int,
                        var chapter: Int,
                        var verse: Int,
                        var verseText: String
                       ) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    fun convertToVerse(): Verse = Verse(book, chapter, verse, verseText)
}