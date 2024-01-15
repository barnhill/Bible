package com.pnuema.bible.android.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface VersionOfflineDao {
    @Transaction
    @Query("select * from offlineVersion")
    suspend fun getVersions(): List<VersionOffline>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun putVersions(version: List<VersionOffline>)

    @Transaction
    @Query("update offlineVersion set completeOffline = :isAvailableOffline where abbreviation = :version")
    suspend fun markCompleteOfflineAvailable(version: String, isAvailableOffline: Boolean)
}
