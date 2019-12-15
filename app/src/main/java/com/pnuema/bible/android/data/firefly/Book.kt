package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IBook

data class Book(
    val book_id: Int,
    val title: String,
    val isNewTestament: Boolean
) : IBook {
    override fun getId(): Int {
        return book_id
    }

    override fun getName(): String {
        return title
    }

    override fun getAbbreviation(): String {
        return "" //TODO if API is modified to return abbreviation this is here to support that
    }
}
