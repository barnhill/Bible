package com.pnuema.bible.android.datasource

import com.pnuema.bible.android.data.firefly.*
import kotlinx.coroutines.flow.Flow

interface FireflyDataSource {
    suspend fun getVersions(): Flow<List<Version>>

    suspend fun getBooks(): Flow<List<Book>>

    suspend fun getChapters(book: Int): Flow<ChapterCountDomain>

    suspend fun getVerseCount(version: String, book: Int, chapter: Int): Flow<VerseCountDomain>

    suspend fun getVerses(version: String, book: Int, chapter: Int): Flow<List<Verse>>

    suspend fun getVersesByBook(version: String, book: Int): List<Verse>

    suspend fun searchVerses(query: String): Flow<VersesDomain>
}