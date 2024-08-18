package com.pnuema.bible.android.data.firefly

import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.database.VersionOffline
import kotlinx.serialization.Serializable

@Serializable
data class Version(
    val version: String,
    val url: String,
    val publisher: String,
    override val copyright: String,
    val copyright_info: String,
    override val abbreviation: String,
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
        abbreviation = abbreviation,
        id = id,
        completeOffline = completeOffline
    )
}
