package com.pnuema.bible.android.database

import androidx.room.*
import com.pnuema.bible.android.data.firefly.VerseCount

@Dao
interface VerseCountOfflineDao {
    @Transaction
    @Query("select * from offlineVerseCount where version = :version AND book_id = :bookId AND chapter = :chapterId")
    suspend fun getVerseCount(version: String, bookId: Int, chapterId: Int): VerseCount?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putVerseCount(verseCount: VerseCountOffline)
}
