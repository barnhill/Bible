package com.pnuema.bible.android.datasource

import com.pnuema.bible.android.data.firefly.*
import com.pnuema.bible.android.database.BookOffline
import com.pnuema.bible.android.database.VerseOffline
import com.pnuema.bible.android.database.VersionOffline
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getVersions(): Flow<List<VersionOffline>>

    suspend fun getBooks(): Flow<List<BookOffline>>

    suspend fun getChapters(version: String, book: Int): Flow<ChapterCountDomain?>

    suspend fun getVerseCount(version: String, book: Int, chapter: Int): Flow<VerseCountDomain?>

    suspend fun getVerses(version: String, book: Int, chapter: Int): Flow<List<VerseOffline>>

    suspend fun searchVerses(query: String): Flow<Verses>
}