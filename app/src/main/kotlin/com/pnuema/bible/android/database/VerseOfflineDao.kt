package com.pnuema.bible.android.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VerseOfflineDao {
    @Transaction
    @Query("select * from offlineVerses where book = :book and chapter = :chapter and version = :version order by verse")
    suspend fun getVerses(version: String, book: Int, chapter: Int): List<VerseOffline>

    @Transaction
    @Query("select * from offlineVerses where book = :book and version = :version order by book, chapter, verse")
    suspend fun getVersesByBook(version: String, book: Int): List<VerseOffline>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putVerses(books: List<VerseOffline>)

    @Transaction
    @Query("""
            SELECT o.* FROM offlineVerses o
            JOIN offlineVerses_fts fts ON o.version = fts.version AND o.book = fts.book AND o.chapter = fts.chapter AND o.verse = fts.verse 
            WHERE offlineVerses_fts MATCH :query
    """)
    suspend fun searchVerses(query: String): List<VerseOffline>
}