package com.pnuema.bible.android.ui.fragments.uiStates

import com.pnuema.bible.android.ui.viewstates.VerseCountViewState

sealed class VersesUiState {
    object Idle: VersesUiState()
    object Loading: VersesUiState()
    object NotLoading: VersesUiState()

    class VersesState(val viewState: VerseCountViewState): VersesUiState()
}