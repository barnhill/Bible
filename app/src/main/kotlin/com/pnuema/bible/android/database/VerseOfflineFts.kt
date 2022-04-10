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
    var version: String,
    @ColumnInfo(name = "book")
    var book: String,
    @ColumnInfo(name = "chapter")
    var chapter: String,
    @ColumnInfo(name = "verse")
    var verse: String,
    @ColumnInfo(name = "verseText")
    var verseText: String,
)