package com.pnuema.bible.android.ui.dialogs

interface VersionSelectionListener {
    fun onVersionSelected(version: String)
    fun onVersionDownloadClicked(version: String)
}