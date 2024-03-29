package com.pnuema.bible.android.data

import com.pnuema.bible.android.database.VerseOffline

interface IVerse {
    fun getText(): String
    fun getChapterNumber(): Int
    fun getVerseNumber(): Int
    fun convertToOfflineModel(version: String): VerseOffline
}
