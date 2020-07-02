package com.pnuema.bible.android.database

import androidx.room.Entity
import com.pnuema.bible.android.data.firefly.VerseCount

@Entity(tableName = "offlineVerseCount", primaryKeys = ["book_id","version"])
data class VerseCountOffline (
    var book_id: Int,
    var chapter: Int,
    var version: String,
    val verseCount: Int = 0
) {
    fun convertToVerseCount(): VerseCount = VerseCount(verseCount)
}

