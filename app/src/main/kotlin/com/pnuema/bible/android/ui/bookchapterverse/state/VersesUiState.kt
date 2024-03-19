package com.pnuema.bible.android.ui.bookchapterverse.state

sealed class VersesUiState {
    object Idle: VersesUiState()
    object Loading: VersesUiState()
    object NotLoading: VersesUiState()

    class VersesState(val viewState: VerseCountViewState): VersesUiState()
}