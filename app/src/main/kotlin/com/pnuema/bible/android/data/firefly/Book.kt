package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.database.BookOffline
import com.squareup.moshi.Json

data class Book(
    val book_id: Int,
    val title: String,
    @Json(name = "newtestament")
    val isNewTestament: Int
) : IBook {
    override fun getId() = book_id

    override fun getName() = title

    override fun getAbbreviation() = "" //TODO if API is modified to return abbreviation this is here to support that

    override fun convertToOfflineModel(version: String) = BookOffline(book_id = book_id, title = title, version = version, newTestament = isNewTestament == 1)
}
