package com.pnuema.bible.android.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pnuema.bible.android.data.firefly.Book

@Entity(tableName = "offlineBooks")
data class BookOffline(var book_id: Int,
                       var version: String,
                       var title: String,
                       var newTestament: Boolean
                       )
{
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    fun convertToBook(): Book = Book(book_id, title, newTestament)
}