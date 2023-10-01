package com.pnuema.bible.android.ui.read.state

sealed class VersionUiState {
    object Idle: VersionUiState()

    data class Versions(val versions: List<VersionViewState>): VersionUiState()
}
