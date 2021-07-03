package com.pnuema.bible.android.retrievers

import com.pnuema.bible.android.data.firefly.Books
import com.pnuema.bible.android.data.firefly.ChapterCount
import com.pnuema.bible.android.data.firefly.VerseCount
import com.pnuema.bible.android.data.firefly.Verses
import com.pnuema.bible.android.data.firefly.Versions

abstract class BaseRetriever internal constructor() {

    companion object {
        var instance: BaseRetriever? = null
    }

    val tag: String get() = javaClass.simpleName

    abstract fun savePrefs()

    abstract fun readPrefs()

    abstract suspend fun getVersions(): Versions

    abstract suspend fun getBooks(): Books

    abstract suspend fun getChapters(book: String): ChapterCount

    abstract suspend fun getVerseCount(version: String, book: String, chapter: String): VerseCount

    abstract suspend fun getVerses(version: String, book: String, chapter: String): Verses
}
