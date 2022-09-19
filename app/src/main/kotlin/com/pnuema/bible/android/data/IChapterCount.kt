package com.pnuema.bible.android.data

import com.pnuema.bible.android.database.ChapterCountOffline
import com.pnuema.bible.android.ui.viewstates.ChapterCountViewState

interface IChapterCount {
    fun convertToOfflineModel(version: String, bookId: Int): ChapterCountOffline
    fun convertToViewState(): ChapterCountViewState
}
