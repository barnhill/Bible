package com.pnuema.bible.android.ui.versionselection.state

import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.data.firefly.VersionsDomain

data class VersionsState(
    val versions: VersionsDomain = VersionsDomain(),
    val removeDialogVersion: IVersion? = null,
    val downloadDialogVersion: IVersion? = null,
    val downloadProgress: DownloadProgress? = null,
)
