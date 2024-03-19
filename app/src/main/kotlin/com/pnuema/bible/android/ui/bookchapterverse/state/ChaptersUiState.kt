package com.pnuema.bible.android.ui.bookchapterverse.state

sealed class ChaptersUiState {
    object Idle: ChaptersUiState()
    object Loading: ChaptersUiState()
    object NotLoading: ChaptersUiState()

    class ChaptersState(val viewState: ChapterCountViewState): ChaptersUiState()
}

