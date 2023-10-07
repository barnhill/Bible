package com.pnuema.bible.android.data

import com.pnuema.bible.android.database.VersionOffline

interface IVersion {
    val id: Int
    val language: String
    val abbreviation: String
    val copyright: String

    fun getDisplayText(): String
    fun convertToOfflineModel(): VersionOffline
}
