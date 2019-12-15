package com.pnuema.bible.android.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BooksOfflineDao {
    @Query("select * from offlineBooks order by book_id")
    fun getBooks(): List<BooksOffline>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putBooks(books: List<BooksOffline>)
}
