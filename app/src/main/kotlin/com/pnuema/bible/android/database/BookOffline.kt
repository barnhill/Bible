package com.pnuema.bible.android.database

import androidx.room.Entity
import com.pnuema.bible.android.data.firefly.Book

@Entity(tableName = "offlineBooks", primaryKeys = ["book_id", "version"])
data class BookOffline(
    val book_id: Int,
    val version: String,
    val title: String,
    val newTestament: Boolean
) {
    fun convertToBook(): Book = Book(book_id, title, if (newTestament) 1 else 0)

}