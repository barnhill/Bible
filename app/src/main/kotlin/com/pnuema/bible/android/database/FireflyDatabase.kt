package com.pnuema.bible.android.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pnuema.bible.android.statics.App

@Database(
    entities = [
        VerseOffline::class,
        BookOffline::class,
        ChapterCountOffline::class,
        VerseCountOffline::class,
        VersionOffline::class,
        VerseOfflineFts::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class FireflyDatabase : RoomDatabase() {
    abstract val verseDao: VerseOfflineDao
    abstract val booksDao: BooksOfflineDao
    abstract val chapterCountDao: ChapterCountOfflineDao
    abstract val verseCountDao: VerseCountOfflineDao
    abstract val versionDao: VersionOfflineDao

    companion object {
        private val INSTANCE: FireflyDatabase by lazy {
            synchronized(FireflyDatabase::class) {
                Room.databaseBuilder(
                    App.context,
                    FireflyDatabase::class.java, FILENAME
                ).build()
            }
        }
        private const val FILENAME = "firefly-offline.db"

        fun getInstance(): FireflyDatabase = INSTANCE
    }
}