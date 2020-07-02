package com.pnuema.bible.android.data.firefly

import androidx.core.content.ContextCompat
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.database.VerseOffline
import com.pnuema.bible.android.statics.App

data class Verse(
        var book: Int = 0,
        var chapter: Int = 0,
        var verse: Int = 0,
        var verseText: String
) : IVerse {
    override fun getText(): String {
        verseText = verseText.replace("¶", "")
        return "<font color=\"#" + String.format("#%06x", ContextCompat.getColor(App.getContext(), R.color.secondary_text) and 0xffffff) + "\"><small>" + verse + "&nbsp;&nbsp;</small></font>" + verseText
    }

    override fun convertToOfflineModel(version: String) = VerseOffline(version = version, book = book, chapter = chapter, verse = verse, verseText = verseText)
}
