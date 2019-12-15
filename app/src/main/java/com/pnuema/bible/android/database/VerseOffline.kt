package com.pnuema.bible.android.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offlineVerses")
data class VerseOffline(@PrimaryKey(autoGenerate = true) var id: Long,
                        var version: String,
                        var book: Int,
                        var chapter: Int,
                        var verse: Int,
                        var verseText: String
                       )