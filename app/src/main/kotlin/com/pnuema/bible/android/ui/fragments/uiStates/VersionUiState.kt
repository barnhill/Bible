package com.pnuema.bible.android.ui.fragments.uiStates

import com.pnuema.bible.android.ui.viewstates.VersionViewState

sealed class VersionUiState {
    object Idle: VersionUiState()

    data class Versions(val versions: List<VersionViewState>): VersionUiState()
}
