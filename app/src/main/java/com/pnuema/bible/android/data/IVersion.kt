package com.pnuema.bible.android.data

interface IVersion {
    val id: Int
    val language: String
    val abbreviation: String

    fun getDisplayText(): String
}
