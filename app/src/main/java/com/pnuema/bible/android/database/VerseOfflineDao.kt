package com.pnuema.bible.android.database

import androidx.room.*

@Dao
interface VerseOfflineDao {
    @Query("select * from offlineVerses where book = :book and chapter = :chapter order by verse")
    suspend fun getVerses(book: Int, chapter: Int): List<VerseOffline>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putVerses(books: List<VerseOffline>)
}