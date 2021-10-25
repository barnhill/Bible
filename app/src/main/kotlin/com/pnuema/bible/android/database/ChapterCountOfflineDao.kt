package com.pnuema.bible.android.database

import androidx.room.*
import com.pnuema.bible.android.data.firefly.ChapterCount

@Dao
interface ChapterCountOfflineDao {
    @Transaction
    @Query("select * from offlineChapterCount where version = :version AND book_id = :bookId")
    suspend fun getChapterCount(version: String, bookId: Int): ChapterCount?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putChapterCount(chapterCount: ChapterCountOffline)
}
