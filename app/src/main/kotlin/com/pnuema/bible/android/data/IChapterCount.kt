package com.pnuema.bible.android.data

import com.pnuema.bible.android.database.ChapterCountOffline

interface IChapterCount {
    fun convertToOfflineModel(version: String, bookId: Int): ChapterCountOffline
}
