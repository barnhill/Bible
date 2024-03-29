package com.pnuema.bible.android.data

import com.pnuema.bible.android.ui.bookchapterverse.state.BookViewState

interface IBookProvider {
    val books: List<IBook>

    fun convertToViewStates(): List<BookViewState>
}
