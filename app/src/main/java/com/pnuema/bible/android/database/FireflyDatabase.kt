package com.pnuema.bible.android.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pnuema.bible.android.statics.App

@Database(entities = [VerseOffline::class,BookOffline::class], version = 1)
abstract class FireflyDatabase : RoomDatabase() {
    abstract fun verseDao(): VerseOfflineDao
    abstract fun booksDao(): BooksOfflineDao

    companion object {
        private lateinit var INSTANCE: FireflyDatabase
        private const val FILENAME = "firefly-offline.db"

        fun getInstance(): FireflyDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(FireflyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(App.getContext(),
                            FireflyDatabase::class.java, FILENAME)
                            .build()
                }
            }
            return INSTANCE
        }
    }
}