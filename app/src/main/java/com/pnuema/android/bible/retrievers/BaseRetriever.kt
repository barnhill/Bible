package com.pnuema.android.bible.retrievers

import androidx.lifecycle.LiveData
import com.pnuema.android.bible.data.firefly.*

abstract class BaseRetriever internal constructor() {

    companion object {
        var instance: BaseRetriever? = null
    }

    val tag: String
        get() = javaClass.simpleName

    abstract fun savePrefs()

    abstract fun readPrefs()

    abstract fun getVersions(): LiveData<Versions>

    abstract fun getBooks(): LiveData<Books>

    abstract fun getChapters(book: String): LiveData<ChapterCount>

    abstract fun getVerseCount(version: String, book: String, chapter: String): LiveData<VerseCount>

    abstract fun getVerses(version: String, book: String, chapter: String): LiveData<Verses>
}
