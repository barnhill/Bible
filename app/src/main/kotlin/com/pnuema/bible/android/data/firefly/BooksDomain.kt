package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.data.IBookProvider
import com.pnuema.bible.android.ui.viewstates.BookViewState

class BooksDomain(override val books: List<IBook>) : IBookProvider {
    override fun convertToViewStates(): List<BookViewState> = books.map { BookViewState(it.getId(), it.getName(), it.getAbbreviation()) }
}
