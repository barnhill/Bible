package com.pnuema.bible.android.retrievers

import com.pnuema.bible.android.data.firefly.*

abstract class BaseRetriever internal constructor() {

    companion object {
        var instance: BaseRetriever? = null
    }

    val tag: String
        get() = javaClass.simpleName

    abstract fun savePrefs()

    abstract fun readPrefs()

    abstract suspend fun getVersions(): Versions

    abstract suspend fun getBooks(): Books

    abstract suspend fun getChapters(book: String): ChapterCount

    abstract suspend fun getVerseCount(version: String, book: String, chapter: String): VerseCount

    abstract suspend fun getVerses(version: String, book: String, chapter: String): Verses
}
