package com.pnuema.bible.android.database

import androidx.room.Entity
import com.pnuema.bible.android.data.firefly.Book

@Entity(tableName = "offlineBooks", primaryKeys = ["book_id","version"])
data class BookOffline(var book_id: Int,
                       var version: String,
                       var title: String,
                       var newTestament: Boolean
                       )
{
    fun convertToBook(): Book = Book(book_id, title, newTestament.compareTo(false))
}