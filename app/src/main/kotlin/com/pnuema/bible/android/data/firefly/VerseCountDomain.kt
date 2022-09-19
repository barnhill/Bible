package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IVerseCount
import com.pnuema.bible.android.database.VerseCountOffline
import com.pnuema.bible.android.ui.viewstates.VerseCountViewState

data class VerseCountDomain (
    val verseCount: Int = 0
): IVerseCount {
    override fun convertToOfflineModel(version: String, bookId: Int, chapterId: Int): VerseCountOffline = VerseCountOffline(book_id = bookId, chapter = chapterId, version = version, verseCount = verseCount)
    override fun convertToViewState(): VerseCountViewState = VerseCountViewState(verseCount)
}
