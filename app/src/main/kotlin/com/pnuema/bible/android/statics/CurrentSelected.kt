package com.pnuema.bible.android.statics

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
            Json.decodeFromString<SelectionSavedData>(it).let { savedData ->
                this@CurrentSelected.version = savedData.version
                this@CurrentSelected.book = savedData.book
                this@CurrentSelected.chapter = savedData.chapter
                this@CurrentSelected.verse = savedData.verse
            }
        }
    }

    suspend fun savePreferences() {
        App.context.dataStore.edit {
            it[stringPreferencesKey(PREF_NAME_DATA_STORAGE)] = Json.encodeToString(SelectionSavedData(version, book, chapter, verse))
        }
    }

    @kotlinx.serialization.Serializable
    data class SelectionSavedData(
        val version: String = "kjv",
        val book: Int = DEFAULT_VALUE,
        val chapter: Int = DEFAULT_VALUE,
        val verse: Int = DEFAULT_VALUE
    )

    override fun toString(): String = "version=$version, book=$book, chapter=$chapter, verse=$verse"
}
