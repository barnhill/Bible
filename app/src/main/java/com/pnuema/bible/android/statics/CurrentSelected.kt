package com.pnuema.bible.android.statics

import android.preference.PreferenceManager
import com.pnuema.bible.android.retrievers.FireflyRetriever

object CurrentSelected {
    var version = "kjv"
    var book: Int? = null
        get() {
            if (field == null) {
                book = 1
            }
            return field
        }
    var chapter: Int? = null
        get() {
            if (field == null) {
                chapter = 1
            }
            return field
        }
    var verse: Int? = null
        get() {
            if (field == null) {
                verse = 1
            }
            return field
        }
    private val mRetriever = FireflyRetriever()

    fun clearVerse() {
        verse = null
    }

    fun clearChapter() {
        chapter = null
    }

    fun readPreferences() {
        mRetriever.readPrefs()
    }

    fun savePreferences() {
        mRetriever.savePrefs()
    }

    fun savePref(prefName: String, prefValue: String) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(App.getContext())
        val editor = sharedPref.edit()
        editor.putString(prefName, prefValue)
        editor.apply()
    }
}
