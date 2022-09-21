package com.pnuema.bible.android.ui.fragments.uiStates

import com.pnuema.bible.android.ui.viewstates.VerseViewState

sealed class ReadUiState {
    object Idle: ReadUiState()
    data class Verses(val verses: List<VerseViewState>): ReadUiState()
}
