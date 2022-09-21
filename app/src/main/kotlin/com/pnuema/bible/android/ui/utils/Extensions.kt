package com.pnuema.bible.android.ui.utils

import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.ui.viewstates.BookViewState
import com.pnuema.bible.android.ui.viewstates.VerseViewState
import com.pnuema.bible.android.ui.viewstates.VersionViewState

object Extensions {
    fun IVerse.toViewState(): VerseViewState = VerseViewState(
        verseNumber = getVerseNumber(),
        verseText = getText(),
    )

    fun IVersion.toViewState(): VersionViewState = VersionViewState(
        abbreviation = abbreviation,
        text = getDisplayText(),
    )

    fun IBook.toViewState(): BookViewState = BookViewState(
        id = getId(),
        abbreviation = getAbbreviation(),
        name = getName(),
    )
}