package com.pnuema.bible.android.ui.read.state

import com.pnuema.bible.android.ui.bookchapterverse.bookselection.state.BookViewState

sealed class ReadBookUiState {
    object Idle: ReadBookUiState()

    data class Books(val books: List<BookViewState>): ReadBookUiState()
}
