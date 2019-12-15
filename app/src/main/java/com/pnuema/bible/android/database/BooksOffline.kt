package com.pnuema.bible.android.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offlineBooks")
data class BooksOffline(@PrimaryKey(autoGenerate = true) var id: Long,
                        var book_id: Int,
                        var version: String,
                        var title: String,
                        var newTestament: Boolean
                       )