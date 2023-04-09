package com.pnuema.bible.android.ui.utils

import androidx.core.content.ContextCompat
import androidx.core.content.res.TypedArrayUtils.getText
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.statics.App
import com.pnuema.bible.android.ui.viewstates.VerseViewState

object VerseUtils {
    fun VerseViewState.formatted(): String = verseText
        .trimMargin("¶")
        .trim()
        .let {
            "&nbsp;&nbsp;&nbsp;&nbsp;<font color=\"#" + String.format("#%06x", ContextCompat.getColor(App.context, R.color.secondary_text) and 0xffffff) + "\"><small>" + verseNumber + "&nbsp;&nbsp;</small></font>" + it
        }
}