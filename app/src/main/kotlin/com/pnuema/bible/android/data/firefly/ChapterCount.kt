package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IChapterCount
import com.pnuema.bible.android.database.ChapterCountOffline

data class ChapterCount (
    val chapterCount: Int = 0
): IChapterCount {

    override fun convertToOfflineModel(version: String, bookId: Int): ChapterCountOffline = ChapterCountOffline(book_id = bookId, version = version, chapterCount = chapterCount)
}
