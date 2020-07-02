package com.pnuema.bible.android.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pnuema.bible.android.data.firefly.Version

@Entity(tableName = "offlineVersion")
data class VersionOffline (
        @PrimaryKey var version: String,
        var url: String,
        var publisher: String,
        var copyright: String,
        var copyright_info: String,
        var language: String,
        var abbreviation: String,
        val id: Int
) {
    fun getDisplayText(): String {
        return version
    }

    fun convertToVersion()
            = Version(version = version, url = url, publisher = publisher, copyright = copyright, copyright_info = copyright_info, language = language, abbreviation = abbreviation, id = id)
}

