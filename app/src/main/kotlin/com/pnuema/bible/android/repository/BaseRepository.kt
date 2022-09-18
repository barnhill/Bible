package com.pnuema.bible.android.repository

import com.pnuema.bible.android.data.firefly.Books
import com.pnuema.bible.android.data.firefly.ChapterCount
import com.pnuema.bible.android.data.firefly.VerseCount
import com.pnuema.bible.android.data.firefly.Verses
import com.pnuema.bible.android.data.firefly.Versions

abstract class BaseRepository internal constructor() {

    companion object {
        var instance: BaseRepository? = null
    }

    val tag: String get() = javaClass.simpleName

    abstract suspend fun getVersions(): Versions

    abstract suspend fun getBooks(): Books

    abstract suspend fun getChapters(book: String): ChapterCount

    abstract suspend fun getVerseCount(version: String, book: String, chapter: String): VerseCount

    abstract suspend fun getVerses(version: String, book: String, chapter: String): Verses

    abstract suspend fun searchVerses(query: String): Verses
}
