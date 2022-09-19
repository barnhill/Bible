package com.pnuema.bible.android.ui.fragments.uiStates

import com.pnuema.bible.android.ui.viewstates.BookViewState

sealed class BooksUiState {
    object Idle: BooksUiState()
    object Loading: BooksUiState()
    object NotLoading: BooksUiState()

    class BooksState(val viewStates: List<BookViewState>): BooksUiState()
}