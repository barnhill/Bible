package com.pnuema.bible.android.statics

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.pnuema.bible.android.retrievers.FireflyRetriever
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.Serializable
import java.util.prefs.Preferences

object CurrentSelected {
    const val DEFAULT_VALUE = 1
    private const val PREF_NAME_DATA_STORAGE = "currentSelected"
    var version = "kjv"
    var book: Int = DEFAULT_VALUE
    var chapter: Int = DEFAULT_VALUE
    var verse: Int = DEFAULT_VALUE

    private val Context.dataStore by preferencesDataStore(name = "currentSelectedDataStore")

    fun clearVerse() {
        verse = DEFAULT_VALUE
    }

    fun clearChapter() {
        chapter = DEFAULT_VALUE
    }

    suspend fun readPreferences() {
        App.context.dataStore.data.first()[stringPreferencesKey(PREF_NAME_DATA_STORAGE)]?.let {
            with(GsonProvider.get().fromJson(it, SelectionSavedData::class.java)) {
                this@CurrentSelected.version = version
                this@CurrentSelected.book = book
                this@CurrentSelected.chapter = chapter
                this@CurrentSelected.verse = verse
            }
        }
    }

    suspend fun savePreferences() {
        App.context.dataStore.edit {
            it[stringPreferencesKey(PREF_NAME_DATA_STORAGE)] = GsonProvider.get().toJson(SelectionSavedData(version, book, chapter, verse))
        }
    }

    data class SelectionSavedData(val version: String = "kjv",
                                  val book: Int = DEFAULT_VALUE,
                                  val chapter: Int = DEFAULT_VALUE,
                                  val verse: Int = DEFAULT_VALUE
    ): Serializable

    override fun toString(): String = "version=$version, book=$book, chapter=$chapter, verse=$verse"
}
