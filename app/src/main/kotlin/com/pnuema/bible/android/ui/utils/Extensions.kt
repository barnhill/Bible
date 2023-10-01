package com.pnuema.bible.android.ui.utils

import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.ui.bookchapterverse.bookselection.state.BookViewState
import com.pnuema.bible.android.ui.read.state.VerseViewState
import com.pnuema.bible.android.ui.read.state.VersionViewState
import java.util.Locale

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

fun String.capitalizeTitleCase(): String {
    return lowercase().trim().split("\\s+".toRegex())
        .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase(Locale.getDefault()) } }
}