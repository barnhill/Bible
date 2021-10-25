package com.pnuema.bible.android.database

import androidx.room.Entity
import com.pnuema.bible.android.data.firefly.ChapterCount

@Entity(tableName = "offlineChapterCount", primaryKeys = ["book_id","version"])
data class ChapterCountOffline (
    var book_id: Int,
    var version: String,
    val chapterCount: Int = 0
) {
    fun convertToChapterCount(): ChapterCount = ChapterCount(chapterCount)
}

