package com.pnuema.bible.android.repository

import com.pnuema.bible.android.data.firefly.BooksDomain
import com.pnuema.bible.android.data.firefly.ChapterCountDomain
import com.pnuema.bible.android.data.firefly.VerseCountDomain
import com.pnuema.bible.android.data.firefly.Verses
import com.pnuema.bible.android.data.firefly.Versions
import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    suspend fun getVersions(): Flow<Versions>

    suspend fun getBooks(): Flow<BooksDomain>

    suspend fun getChapters(version: String, book: Int): Flow<ChapterCountDomain>

    suspend fun getVerseCount(version: String, book: Int, chapter: Int): Flow<VerseCountDomain>

    suspend fun getVerses(version: String, book: Int, chapter: Int): Flow<Verses>

    suspend fun searchVerses(query: String): Verses
}
