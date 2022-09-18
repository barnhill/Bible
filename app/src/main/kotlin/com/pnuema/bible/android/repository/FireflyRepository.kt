package com.pnuema.bible.android.repository

import android.content.Context
import android.net.ConnectivityManager
import com.pnuema.bible.android.api.FireflyAPI
import com.pnuema.bible.android.api.IFireflyAPI
import com.pnuema.bible.android.data.firefly.*
import com.pnuema.bible.android.database.FireflyDatabase
import com.pnuema.bible.android.statics.App
import com.pnuema.bible.android.statics.CurrentSelected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FireflyRepository : BaseRepository() {
    private val connMgr = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private fun isNetworkConnected(): Boolean {
        return connMgr.activeNetworkInfo?.isConnected ?: false
    }

    override suspend fun getVersions(): Versions = withContext(Dispatchers.IO) {
        //TODO: only get offline version if not connected
        val offlineVersions = FireflyDatabase.getInstance().versionDao.getVersions()
        if (offlineVersions.isNotEmpty()) {
            return@withContext Versions(offlineVersions.map { it.convertToVersion() })
        }

        if (!isNetworkConnected()) {
            return@withContext Versions(listOf())
        }

        val api = FireflyAPI.create<IFireflyAPI>()
        val versions = api.getVersions(null) //TODO select language

        FireflyDatabase.getInstance().versionDao.putVersions(versions.map { it.convertToOfflineModel() })

        return@withContext Versions(versions)
    }

    override suspend fun getChapters(book: String): ChapterCount = withContext(Dispatchers.IO) {
        //TODO: only get offline version if current selected version is marked as offline ready or offline
        val currentBook = CurrentSelected.book
        val offlineChapterCount = FireflyDatabase.getInstance().chapterCountDao.getChapterCount(CurrentSelected.version, currentBook)
        if (offlineChapterCount != null && offlineChapterCount.chapterCount > 0) {
            return@withContext ChapterCount(offlineChapterCount.chapterCount)
        }

        if (!isNetworkConnected()) {
            return@withContext ChapterCount()
        }

        val api = FireflyAPI.create<IFireflyAPI>()
        val chapterCount = api.getChapterCount(currentBook, CurrentSelected.version)
        FireflyDatabase.getInstance().chapterCountDao.putChapterCount(chapterCount.convertToOfflineModel(CurrentSelected.version, currentBook))

        return@withContext chapterCount
    }

    override suspend fun getVerseCount(version: String, book: String, chapter: String): VerseCount = withContext(Dispatchers.IO) {
        //TODO: only get offline version if current selected version is marked as offline ready or offline
        val currentBook = CurrentSelected.book
        val currentChapter = CurrentSelected.chapter
        val offlineVerseCount = FireflyDatabase.getInstance().verseCountDao.getVerseCount(CurrentSelected.version, currentBook, currentChapter)
        if (offlineVerseCount != null && offlineVerseCount.verseCount > 0) {
            return@withContext VerseCount(offlineVerseCount.verseCount)
        }

        if (!isNetworkConnected()) {
            return@withContext VerseCount()
        }

        val api = FireflyAPI.create<IFireflyAPI>()
        val verseCount = api.getVerseCount(book, chapter, version)
        FireflyDatabase.getInstance().verseCountDao.putVerseCount(verseCount.convertToOfflineModel(version, currentBook, currentChapter))

        return@withContext verseCount
    }

    override suspend fun getBooks(): Books {
        //TODO: only get offline version if current selected version is marked as offline ready or offline
        val offlineBooks = FireflyDatabase.getInstance().booksDao.getBooks(CurrentSelected.version)
        if (offlineBooks.isNotEmpty()) {
            return Books(offlineBooks.map { it.convertToBook() })
        }

        if (!isNetworkConnected()) {
            return Books(listOf())
        }

        val api = FireflyAPI.create<IFireflyAPI>()
        val books = api.getBooks(CurrentSelected.version)

        FireflyDatabase.getInstance().booksDao.putBooks(books.map { it.convertToOfflineModel(CurrentSelected.version) })

        return Books(books)
    }

    override suspend fun getVerses(version: String, book: String, chapter: String): Verses {
        //TODO: only get offline version if current selected version is marked as offline ready or offline
        val offlineVerses = FireflyDatabase.getInstance().verseDao.getVerses(CurrentSelected.version, CurrentSelected.book, CurrentSelected.chapter)
        if (offlineVerses.isNotEmpty()) {
            return Verses(offlineVerses.map { it.convertToVerse() })
        }

        if (!isNetworkConnected()) {
            return Verses(listOf())
        }

        val api = FireflyAPI.create<IFireflyAPI>()
        val verses = api.getChapterVerses(book, chapter, version)
        FireflyDatabase.getInstance().verseDao.putVerses(verses.map { it.convertToOfflineModel(CurrentSelected.version) })

        return Verses(verses)
    }

    override suspend fun searchVerses(query: String): Verses = Verses(FireflyDatabase.getInstance().verseDao.searchVerses(query = query).map { it.convertToVerse() })
}
