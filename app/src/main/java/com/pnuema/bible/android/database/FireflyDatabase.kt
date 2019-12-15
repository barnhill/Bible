package com.pnuema.bible.android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VerseOffline::class,BooksOffline::class], version = 1)
abstract class FireflyDatabase : RoomDatabase() {
    abstract fun verseDao(): VerseOfflineDao
    abstract fun booksDao(): BooksOfflineDao

    companion object {
        private var INSTANCE: FireflyDatabase? = null

        fun getInstance(context: Context): FireflyDatabase? {
            if (INSTANCE == null) {
                synchronized(FireflyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            FireflyDatabase::class.java, "firefly-offline.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}