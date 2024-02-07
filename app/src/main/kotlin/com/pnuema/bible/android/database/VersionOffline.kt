package com.pnuema.bible.android.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pnuema.bible.android.data.firefly.Version

@Entity(tableName = "offlineVersion")
data class VersionOffline(
    @PrimaryKey val abbreviation: String,
    val id: Int,
    var version: String,
    val url: String,
    val publisher: String,
    val copyright: String,
    @ColumnInfo(name = "copyright_info") val copyrightInfo: String,
    val completeOffline: Boolean = false
) {
    fun convertToVersion() = Version(
        version = version,
        url = url,
        publisher = publisher,
        copyright = copyright,
        copyright_info = copyrightInfo,
        abbreviation = abbreviation,
        id = id,
        completeOffline = completeOffline
    )
}

