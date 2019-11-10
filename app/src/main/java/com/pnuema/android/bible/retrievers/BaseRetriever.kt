package com.pnuema.android.bible.retrievers

import java.util.*

abstract class BaseRetriever internal constructor() : Observable() {

    val tag: String
        get() = javaClass.simpleName

    abstract fun savePrefs()

    abstract fun readPrefs()

    abstract fun getVersions()

    abstract fun getBooks()

    abstract fun getChapters(book: String)

    abstract fun getVerseCount(version: String, book: String, chapter: String)

    abstract fun getVerses(version: String, book: String, chapter: String)
}
