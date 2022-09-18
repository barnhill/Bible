package com.pnuema.bible.android.repository

import com.pnuema.bible.android.data.firefly.Books
import com.pnuema.bible.android.data.firefly.ChapterCount
import com.pnuema.bible.android.data.firefly.VerseCount
import com.pnuema.bible.android.data.firefly.Verses
import com.pnuema.bible.android.data.firefly.Versions
import kotlinx.coroutines.flow.Flow

interface BaseRepository {

    suspend fun getVersions(): Flow<Versions>

    suspend fun getBooks(): Flow<Books>

    suspend fun getChapters(book: String): Flow<ChapterCount>

    suspend fun getVerseCount(version: String, book: Int, chapter: Int): Flow<VerseCount>

    suspend fun getVerses(version: String, book: Int, chapter: Int): Flow<Verses>

    suspend fun searchVerses(query: String): Verses
}
