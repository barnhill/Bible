package com.pnuema.bible.android.ui.read.state

sealed class ReadUiState {
    data object Idle: ReadUiState()
    data class Verses(val verses: List<VerseViewState>): ReadUiState()
}
