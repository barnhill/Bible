package com.pnuema.bible.android.datasource

import com.pnuema.bible.android.api.FireflyAPI.api
import com.pnuema.bible.android.data.firefly.Book
import com.pnuema.bible.android.data.firefly.ChapterCountDomain
import com.pnuema.bible.android.data.firefly.Verse
import com.pnuema.bible.android.data.firefly.VerseCountDomain
import com.pnuema.bible.android.data.firefly.VersesDomain
import com.pnuema.bible.android.data.firefly.Version
import com.pnuema.bible.android.statics.CurrentSelected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FireflyDataSourceImpl: FireflyDataSource {
    override suspend fun getVersions(): Flow<List<Version>> = flow {
        emit(api.getVersions(null)) //TODO select language
    }

    override suspend fun getBooks(): Flow<List<Book>> = flow {
        emit(api.getBooks(CurrentSelected.version))
    }

    override suspend fun getChapters(book: Int): Flow<ChapterCountDomain> = flow {
        emit(api.getChapterCount(book, CurrentSelected.version))
    }

    override suspend fun getVerseCount(
        version: String,
        book: Int,
        chapter: Int
    ): Flow<VerseCountDomain> = flow {
        emit(api.getVerseCount(book, chapter, version))
    }

    override suspend fun getVerses(version: String, book: Int, chapter: Int): Flow<List<Verse>> = flow {
        emit(api.getChapterVerses(book, chapter, version))
    }

    override suspend fun getVersesByBook(version: String, book: Int): List<Verse> = api.getBookVerses(book, version)

    override suspend fun searchVerses(query: String): Flow<VersesDomain> {
        TODO("Not yet implemented")
    }
}