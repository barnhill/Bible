package com.pnuema.bible.android.repository

import com.pnuema.bible.android.data.firefly.BooksDomain
import com.pnuema.bible.android.data.firefly.ChapterCountDomain
import com.pnuema.bible.android.data.firefly.VerseCountDomain
import com.pnuema.bible.android.data.firefly.VersesDomain
import com.pnuema.bible.android.data.firefly.VersionsDomain
import com.pnuema.bible.android.database.ChapterCountOffline
import com.pnuema.bible.android.database.FireflyDatabase
import com.pnuema.bible.android.database.VerseCountOffline
import com.pnuema.bible.android.datasource.FireflyDataSource
import com.pnuema.bible.android.datasource.LocalDataSource
import com.pnuema.bible.android.statics.App
import com.pnuema.bible.android.statics.ConnectionUtils
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@Reusable
class FireflyRepository @Inject constructor(
    private val remoteDataSource: FireflyDataSource,
    private val localDataSource: LocalDataSource
) : BaseRepository {
    private val isNetworkConnected: Boolean get() = ConnectionUtils.isConnected(App.context)

    override suspend fun getVersions(): Flow<VersionsDomain> = flow {
        //TODO: only get offline version if not connected
        localDataSource.getVersions().collect { offlineVersions ->
            if (offlineVersions.isNotEmpty()) {
                emit(VersionsDomain(offlineVersions.map { it.convertToVersion() }))
                return@collect
            } else {
                if (!isNetworkConnected) {
                    emit(VersionsDomain(emptyList()))
                    return@collect
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
                return@collect
            } else {
                if (!isNetworkConnected) {
                    emit(ChapterCountDomain())
                    return@collect
                }

                remoteDataSource.getChapters(book).collect { chapterCount ->
                    FireflyDatabase.getInstance().chapterCountDao.putChapterCount(chapterCount.convertToOfflineModel(version, book))
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
                return@collect
            } else {
                if (!isNetworkConnected) {
                    emit(VerseCountDomain())
                    return@collect
                }

                remoteDataSource.getVerseCount(version, book, chapter).collect { verseCount ->
                    FireflyDatabase.getInstance().verseCountDao.putVerseCount(verseCount.convertToOfflineModel(version, book, chapter))
                    emit(verseCount)
                }
            }
        }
    }

    override suspend fun getBooks(version: String): Flow<BooksDomain> = flow {
        //TODO: only get offline version if current selected version is marked as offline ready or offline
        localDataSource.getBooks().collect { offlineBooks ->
            if (offlineBooks.isNotEmpty()) {
                emit(BooksDomain(offlineBooks.map { it.convertToBook() }))
                return@collect
            } else {
                if (!isNetworkConnected) {
                    emit(BooksDomain(emptyList()))
                    return@collect
                }

                remoteDataSource.getBooks().collect { books ->
                    FireflyDatabase.getInstance().booksDao.putBooks(books.map { it.convertToOfflineModel(version) })
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
                return@collect
            } else {
                if (!isNetworkConnected) {
                    emit(VersesDomain(emptyList()))
                    return@collect
                }

                remoteDataSource.getVerses(version, book, chapter).collect { verses ->
                    FireflyDatabase.getInstance().verseDao.putVerses(verses.map { it.convertToOfflineModel(version) })
                    emit(VersesDomain(verses))
                }
            }
        }
    }

    override suspend fun getVersesByBook(
        version: String,
        book: Int,
    ): VersesDomain {
        //TODO: only get offline version if current selected version is marked as offline ready or offline
        val offlineVerses = localDataSource.getVersesByBook(version, book)
        if (offlineVerses.isNotEmpty()) {
            return VersesDomain(offlineVerses.map { it.convertToVerse() })
        } else {
            if (!isNetworkConnected) {
                return VersesDomain(emptyList())
            }

            val verses = remoteDataSource.getVersesByBook(version, book)
            with(FireflyDatabase.getInstance()) {
                //store verses for book
                verseDao.putVerses(verses.map { it.convertToOfflineModel(version) })

                val chapterCount = verses.maxOf { it.chapter }

                //store chapter count for book
                chapterCountDao.putChapterCount(
                    ChapterCountOffline(
                        book,
                        version,
                        chapterCount
                    )
                )

                //store verse count for each chapter in book
                for (chapter in 1..chapterCount) {
                    verseCountDao.putVerseCount(
                        VerseCountOffline(
                            book,
                            chapter,
                            version,
                            verses.filter { it.chapter == chapter }.maxOf { it.verse })
                    )
                }
            }
            return VersesDomain(verses)
        }
    }

    override suspend fun removeOfflineVersion(version: String) {
        localDataSource.removeOfflineVersion(version)
    }

    override suspend fun searchVerses(query: String): VersesDomain = VersesDomain(FireflyDatabase.getInstance().verseDao.searchVerses(query = query).map { it.convertToVerse() })
}
