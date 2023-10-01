package com.pnuema.bible.android.ui.bookchapterverse.uiStates

import com.pnuema.bible.android.ui.bookchapterverse.viewstates.VerseCountViewState

sealed class VersesUiState {
    object Idle: VersesUiState()
    object Loading: VersesUiState()
    object NotLoading: VersesUiState()

    class VersesState(val viewState: VerseCountViewState): VersesUiState()
}