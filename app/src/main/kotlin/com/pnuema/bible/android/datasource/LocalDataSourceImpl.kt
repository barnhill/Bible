package com.pnuema.bible.android.datasource

import com.pnuema.bible.android.data.firefly.*
import com.pnuema.bible.android.database.BookOffline
import com.pnuema.bible.android.database.FireflyDatabase
import com.pnuema.bible.android.database.VerseOffline
import com.pnuema.bible.android.database.VersionOffline
import com.pnuema.bible.android.statics.CurrentSelected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalDataSourceImpl: LocalDataSource {
    override suspend fun getVersions(): Flow<List<VersionOffline>> = flow {
        val offlineVersions = FireflyDatabase.getInstance().versionDao.getVersions()
        emit(offlineVersions)
    }

    override suspend fun getBooks(): Flow<List<BookOffline>> = flow {
        val offlineBooks = FireflyDatabase.getInstance().booksDao.getBooks(CurrentSelected.version)
        emit(offlineBooks)
    }

    override suspend fun getChapters(version: String, book: Int): Flow<ChapterCountDomain?> = flow {
        val offlineChapterCount = FireflyDatabase.getInstance().chapterCountDao.getChapterCount(
            version,
            book
        )

        emit(offlineChapterCount)
    }

    override suspend fun getVerseCount(
        version: String,
        book: Int,
        chapter: Int
    ): Flow<VerseCountDomain?> = flow {
        val offlineVerseCount = FireflyDatabase.getInstance().verseCountDao.getVerseCount(
            version, book, chapter
        )
        emit(offlineVerseCount)
    }

    override suspend fun getVerses(version: String, book: Int, chapter: Int): Flow<List<VerseOffline>> = flow {
        val offlineVerses = FireflyDatabase.getInstance().verseDao.getVerses(CurrentSelected.version, CurrentSelected.book, CurrentSelected.chapter)
        emit(offlineVerses)
    }

    override suspend fun searchVerses(query: String): Flow<VersesDomain> {
        TODO("Not yet implemented")
    }
}