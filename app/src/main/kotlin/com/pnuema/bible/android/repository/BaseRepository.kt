package com.pnuema.bible.android.repository

import com.pnuema.bible.android.data.firefly.BooksDomain
import com.pnuema.bible.android.data.firefly.ChapterCountDomain
import com.pnuema.bible.android.data.firefly.VerseCountDomain
import com.pnuema.bible.android.data.firefly.VersesDomain
import com.pnuema.bible.android.data.firefly.VersionsDomain
import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    suspend fun getVersions(): Flow<VersionsDomain>

    suspend fun getBooks(version: String): Flow<BooksDomain>

    suspend fun getChapters(version: String, book: Int): Flow<ChapterCountDomain>

    suspend fun getVerseCount(version: String, book: Int, chapter: Int): Flow<VerseCountDomain>

    suspend fun getVerses(version: String, book: Int, chapter: Int): Flow<VersesDomain>

    suspend fun getVersesByBook(version: String, book: Int): VersesDomain

    suspend fun searchVerses(query: String): VersesDomain

    suspend fun removeOfflineVersion(version: String)
}
