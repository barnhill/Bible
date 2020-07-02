package com.pnuema.bible.android.data

import com.pnuema.bible.android.database.BookOffline

interface IBook {
    fun getId(): Int
    fun getName(): String
    fun getAbbreviation(): String
    fun convertToOfflineModel(version: String): BookOffline
}
