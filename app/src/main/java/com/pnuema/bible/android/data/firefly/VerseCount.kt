package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IVerseCount
import com.pnuema.bible.android.database.VerseCountOffline

data class VerseCount (
    val verseCount: Int = 0
): IVerseCount {
    override fun convertToOfflineModel(version: String, bookId: Int, chapterId: Int): VerseCountOffline = VerseCountOffline(book_id = bookId, chapter = chapterId, version = version, verseCount = verseCount)
}
