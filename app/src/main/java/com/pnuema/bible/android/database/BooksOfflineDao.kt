package com.pnuema.bible.android.database

import androidx.room.*

@Dao
interface BooksOfflineDao {
    @Transaction
    @Query("select * from offlineBooks where version = :version order by book_id")
    suspend fun getBooks(version: String): List<BookOffline>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putBooks(books: List<BookOffline>)
}
