package com.pnuema.bible.android.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.FtsOptions
import com.pnuema.bible.android.database.VerseOffline.Companion.TABLE_NAME_FTS

@Entity(tableName = TABLE_NAME_FTS)
@Fts4(contentEntity = VerseOffline::class, tokenizer = FtsOptions.TOKENIZER_PORTER)
class VerseOfflineFts (
    @ColumnInfo(name = "version")
    val version: String,
    @ColumnInfo(name = "book")
    val book: String,
    @ColumnInfo(name = "chapter")
    val chapter: String,
    @ColumnInfo(name = "verse")
    val verse: String,
    @ColumnInfo(name = "verseText")
    val verseText: String,
)