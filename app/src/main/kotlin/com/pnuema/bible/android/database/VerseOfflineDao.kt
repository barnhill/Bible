package com.pnuema.bible.android.database

import androidx.room.*

@Dao
interface VerseOfflineDao {
    @Transaction
    @Query("select * from offlineVerses where book = :book and chapter = :chapter and version = :version order by verse")
    suspend fun getVerses(version: String, book: Int, chapter: Int): List<VerseOffline>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putVerses(books: List<VerseOffline>)
}