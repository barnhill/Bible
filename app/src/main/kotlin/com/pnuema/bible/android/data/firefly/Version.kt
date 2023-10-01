package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.database.VersionOffline

data class Version(
    var version: String,
    var url: String,
    var publisher: String,
    var copyright: String,
    var copyright_info: String,
    override var language: String,
    override var abbreviation: String,
    override val id: Int,
    val completeOffline: Boolean = false,
) : IVersion {

    override fun getDisplayText(): String {
        return version
    }

    override fun convertToOfflineModel(): VersionOffline = VersionOffline(
        version = version,
        url = url,
        publisher = publisher,
        copyright = copyright,
        copyrightInfo = copyright_info,
        language = language,
        abbreviation = abbreviation,
        id = id,
        completeOffline = completeOffline
    )
}
