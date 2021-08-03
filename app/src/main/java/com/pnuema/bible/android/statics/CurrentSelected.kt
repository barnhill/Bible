package com.pnuema.bible.android.statics

import android.preference.PreferenceManager
import com.pnuema.bible.android.retrievers.FireflyRetriever

object CurrentSelected {
    const val DEFAULT_VALUE = 0
    var version = "kjv"
    var book: Int = DEFAULT_VALUE
    var chapter: Int = DEFAULT_VALUE
    var verse: Int = DEFAULT_VALUE
    private val mRetriever = FireflyRetriever()

    fun clearVerse() {
        verse = DEFAULT_VALUE
    }

    fun clearChapter() {
        chapter = DEFAULT_VALUE
    }

    fun readPreferences() {
        mRetriever.readPrefs()
    }

    fun savePreferences() {
        mRetriever.savePrefs()
    }

    fun savePref(prefName: String, prefValue: String) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(App.context)
        val editor = sharedPref.edit()
        editor.putString(prefName, prefValue)
        editor.apply()
    }
}
