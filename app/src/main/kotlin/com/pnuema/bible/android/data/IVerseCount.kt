package com.pnuema.bible.android.data

import com.pnuema.bible.android.database.VerseCountOffline

interface IVerseCount {
    fun convertToOfflineModel(version: String, bookId: Int, chapterId: Int): VerseCountOffline
}
