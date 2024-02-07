package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.database.BookOffline

data class Book(
    val bookId: Int,
    val title: String,
    val newtestament: Boolean,
) : IBook {
    override fun getId() = bookId

    override fun getName() = title

    override fun getAbbreviation() = "" //TODO if API is modified to return abbreviation this is here to support that

    override fun convertToOfflineModel(version: String) = BookOffline(book_id = bookId, title = title, version = version, newTestament = newtestament)
}
