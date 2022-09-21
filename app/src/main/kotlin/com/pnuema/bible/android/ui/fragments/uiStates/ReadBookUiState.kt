package com.pnuema.bible.android.ui.fragments.uiStates

import com.pnuema.bible.android.ui.viewstates.BookViewState

sealed class ReadBookUiState {
    object Idle: ReadBookUiState()

    data class Books(val books: List<BookViewState>): ReadBookUiState()
}
