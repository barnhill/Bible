package com.pnuema.bible.android.data

import com.pnuema.bible.android.database.VerseCountOffline
import com.pnuema.bible.android.ui.bookchapterverse.state.VerseCountViewState

interface IVerseCount {
    fun convertToOfflineModel(version: String, bookId: Int, chapterId: Int): VerseCountOffline
    fun convertToViewState(): VerseCountViewState
}
