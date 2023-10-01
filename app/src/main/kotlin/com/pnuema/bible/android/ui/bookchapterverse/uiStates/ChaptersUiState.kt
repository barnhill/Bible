package com.pnuema.bible.android.ui.bookchapterverse.uiStates

import com.pnuema.bible.android.ui.bookchapterverse.viewstates.ChapterCountViewState

sealed class ChaptersUiState {
    object Idle: ChaptersUiState()
    object Loading: ChaptersUiState()
    object NotLoading: ChaptersUiState()

    class ChaptersState(val viewState: ChapterCountViewState): ChaptersUiState()
}

