package com.pnuema.bible.android.database

import androidx.room.*

@Dao
interface VersionOfflineDao {
    @Transaction
    @Query("select * from offlineVersion")
    suspend fun getVersions(): List<VersionOffline>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun putVersions(version: List<VersionOffline>)

    @Transaction
    @Query("update offlineVersion set completeOffline = 1 where abbreviation = :version")
    suspend fun markCompleteOfflineAvailable(version: String)
}
