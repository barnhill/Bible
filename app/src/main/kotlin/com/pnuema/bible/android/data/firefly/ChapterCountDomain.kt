package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IChapterCount
import com.pnuema.bible.android.database.ChapterCountOffline
import com.pnuema.bible.android.ui.viewstates.ChapterCountViewState

data class ChapterCountDomain (
    val chapterCount: Int = 0
): IChapterCount {

    override fun convertToOfflineModel(version: String, bookId: Int): ChapterCountOffline = ChapterCountOffline(book_id = bookId, version = version, chapterCount = chapterCount)
    override fun convertToViewState(): ChapterCountViewState = ChapterCountViewState(chapterCount)
}
