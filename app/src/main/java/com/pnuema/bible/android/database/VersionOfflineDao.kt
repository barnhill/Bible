package com.pnuema.bible.android.database

import androidx.room.*

@Dao
interface VersionOfflineDao {
    @Transaction
    @Query("select * from offlineVersion")
    suspend fun getVersions(): List<VersionOffline>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putVersions(version: List<VersionOffline>)
}
