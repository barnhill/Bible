package com.pnuema.bible.android.ui.bookchapterverse.bookselection.state

sealed class BooksUiState {
    object Idle: BooksUiState()
    object Loading: BooksUiState()
    object NotLoading: BooksUiState()

    class BooksState(val viewStates: List<BookViewState>): BooksUiState()
}