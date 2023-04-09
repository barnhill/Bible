package com.pnuema.bible.android.database

import androidx.room.*
import com.pnuema.bible.android.data.firefly.ChapterCountDomain

@Dao
interface ChapterCountOfflineDao {
    @Transaction
    @Query("select chapterCount from offlineChapterCount where version = :version AND book_id = :bookId")
    suspend fun getChapterCount(version: String, bookId: Int): ChapterCountDomain?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putChapterCount(chapterCount: ChapterCountOffline)
}
