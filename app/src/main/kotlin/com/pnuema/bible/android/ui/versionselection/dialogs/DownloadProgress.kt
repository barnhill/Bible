package com.pnuema.bible.android.ui.versionselection.dialogs

data class DownloadProgress (
    val versionToDownload: String = "",
    val versionToDownloadDisplay: String = "",
    val max: Int = Int.MAX_VALUE,
    val progress: Int = 0,
    val isComplete: Boolean = false,
)
