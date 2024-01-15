package com.pnuema.bible.android.ui.versionselection.dialogs

sealed class DownloadProgress {
    data class Max(val max: Int): DownloadProgress()
    data object ProgressByOne: DownloadProgress()
    data object Complete: DownloadProgress()
}
