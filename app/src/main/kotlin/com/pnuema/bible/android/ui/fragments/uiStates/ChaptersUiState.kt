package com.pnuema.bible.android.ui.fragments.uiStates

import com.pnuema.bible.android.ui.viewstates.ChapterCountViewState

sealed class ChaptersUiState {
    object Idle: ChaptersUiState()
    object Loading: ChaptersUiState()
    object NotLoading: ChaptersUiState()

    class ChaptersState(val viewState: ChapterCountViewState): ChaptersUiState()
}

