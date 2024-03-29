package com.pnuema.bible.android.database

import androidx.room.Entity
import com.pnuema.bible.android.data.firefly.VerseCountDomain

@Entity(tableName = "offlineVerseCount", primaryKeys = ["book_id", "chapter", "version"])
data class VerseCountOffline (
    val book_id: Int,
    val chapter: Int,
    val version: String,
    val verseCount: Int = 0
) {
    fun convertToVerseCount(): VerseCountDomain = VerseCountDomain(verseCount)
}

