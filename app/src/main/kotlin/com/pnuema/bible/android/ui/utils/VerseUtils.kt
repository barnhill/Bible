package com.pnuema.bible.android.ui.utils

import androidx.core.content.ContextCompat
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.statics.App

object VerseUtils {
    fun IVerse.formatted(): String = getText()
        .replace("¶", "")
        .let {
            "<font color=\"#" + String.format("#%06x", ContextCompat.getColor(App.context, R.color.secondary_text) and 0xffffff) + "\"><small>" + getVerseNumber() + "&nbsp;&nbsp;</small></font>" + getText()
        }
}