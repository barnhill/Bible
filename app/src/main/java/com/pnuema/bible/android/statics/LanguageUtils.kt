package com.pnuema.bible.android.statics

import java.util.Locale

object LanguageUtils {
    val iSOLanguage: String
        get() = Locale.getDefault().isO3Language.toLowerCase(Locale.getDefault())
}