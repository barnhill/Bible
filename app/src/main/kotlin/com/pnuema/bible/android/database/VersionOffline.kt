package com.pnuema.bible.android.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pnuema.bible.android.data.firefly.Version

@Entity(tableName = "offlineVersion")
data class VersionOffline(
    @PrimaryKey var version: String,
    val url: String,
    val publisher: String,
    val copyright: String,
    val copyright_info: String,
    val language: String,
    val abbreviation: String,
    val id: Int
) {
    fun getDisplayText(): String = version

    fun convertToVersion() = Version(
        version = version,
        url = url,
        publisher = publisher,
        copyright = copyright,
        copyright_info = copyright_info,
        language = language,
        abbreviation = abbreviation,
        id = id
    )
}

