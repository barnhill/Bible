package com.pnuema.bible.android.repository

import android.content.Context
import android.net.ConnectivityManager
import com.pnuema.bible.android.data.firefly.*
import com.pnuema.bible.android.database.FireflyDatabase
import com.pnuema.bible.android.datasource.FireflyDataSource
import com.pnuema.bible.android.datasource.FireflyDataSourceImpl
import com.pnuema.bible.android.datasource.LocalDataSource
import com.pnuema.bible.android.datasource.LocalDataSourceImpl
import com.pnuema.bible.android.statics.App
import com.pnuema.bible.android.statics.CurrentSelected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FireflyRepository(
    private val remoteDataSource: FireflyDataSource = FireflyDataSourceImpl(),
    private val localDataSource: LocalDataSource = LocalDataSourceImpl()
) : BaseRepository {
    private val connMgr = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private fun isNetworkConnected(): Boolean {
        return connMgr.activeNetworkInfo?.isConnected ?: false
    }

    override suspend fun getVersions(): Flow<VersionsDomain> = flow {
        //TODO: only get offline version if not connected
        localDataSource.getVersions().collect { offlineVersions ->
            if (offlineVersions.isNotEmpty()) {
                emit(VersionsDomain(offlineVersions.map { it.convertToVersion() }))
            } else {
                if (!isNetworkConnected()) {
                    emit(VersionsDomain(listOf()))
                }

                remoteDataSource.getVersions().collect { versions ->
                    FireflyDatabase.getInstance().versionDao.putVersions(versions.map { it.convertToOfflineModel() })
                    emit(VersionsDomain(versions))
                }
            }
        }
    }

    override suspend fun getChapters(version: String, book: Int): Flow<ChapterCountDomain> = flow {
        //TODO: only get offline version if current selected version is marked as offline ready or offline
        localDataSource.getChapters(version, book).collect { offlineChapterCount ->
            if (offlineChapterCount != null && offlineChapterCount.chapterCount > 0) {
                emit(ChapterCountDomain(offlineChapterCount.chapterCount))
            } else {
                if (!isNetworkConnected()) {
                    emit(ChapterCountDomain())
                }

                remoteDataSource.getChapters(CurrentSelected.book).collect { chapterCount ->
                    FireflyDatabase.getInstance().chapterCountDao.putChapterCount(chapterCount.convertToOfflineModel(CurrentSelected.version, CurrentSelected.book))
                    emit(chapterCount)
                }
            }
        }
    }

    override suspend fun getVerseCount(version: String, book: Int, chapter: Int): Flow<VerseCountDomain> = flow {
        //TODO: only get offline version if current selected version is marked as offline ready or offline
        localDataSource.getVerseCount(version, book, chapter).collect { offlineVerseCount ->
            if (offlineVerseCount != null && offlineVerseCount.verseCount > 0) {
                emit(VerseCountDomain(offlineVerseCount.verseCount))
            } else {
                if (!isNetworkConnected()) {
                    emit(VerseCountDomain())
                }

                remoteDataSource.getVerseCount(version, book, chapter).collect { verseCount ->
                    FireflyDatabase.getInstance().verseCountDao.putVerseCount(verseCount.convertToOfflineModel(version, book, chapter))
                    emit(verseCount)
                }
            }
        }
    }

    override suspend fun getBooks(): Flow<BooksDomain> = flow {
        //TODO: only get offline version if current selected version is marked as offline ready or offline
        localDataSource.getBooks().collect { offlineBooks ->
            if (offlineBooks.isNotEmpty()) {
                emit(BooksDomain(offlineBooks.map { it.convertToBook() }))
            } else {
                if (!isNetworkConnected()) {
                    emit(BooksDomain(listOf()))
                }

                remoteDataSource.getBooks().collect { books ->
                    FireflyDatabase.getInstance().booksDao.putBooks(books.map { it.convertToOfflineModel(CurrentSelected.version) })
                    emit(BooksDomain(books))
                }
            }
        }
    }

    override suspend fun getVerses(version: String, book: Int, chapter: Int): Flow<VersesDomain> = flow {
        //TODO: only get offline version if current selected version is marked as offline ready or offline
        localDataSource.getVerses(version, book, chapter).collect { offlineVerses ->
            if (offlineVerses.isNotEmpty()) {
                emit(VersesDomain(offlineVerses.map { it.convertToVerse() }))
            } else {
                if (!isNetworkConnected()) {
                    emit(VersesDomain(listOf()))
                }

                remoteDataSource.getVerses(version, book, chapter).collect { verses ->
                    FireflyDatabase.getInstance().verseDao.putVerses(verses.map { it.convertToOfflineModel(CurrentSelected.version) })
                    emit(VersesDomain(verses))
                }
            }
        }
    }

    override suspend fun searchVerses(query: String): VersesDomain = VersesDomain(FireflyDatabase.getInstance().verseDao.searchVerses(query = query).map { it.convertToVerse() })
}
