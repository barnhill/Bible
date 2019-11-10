package com.pnuema.android.bible.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.bible.data.firefly.ChapterCount
import com.pnuema.android.bible.retrievers.FireflyRetriever

class ChaptersViewModel: ViewModel() {
    val chapters: LiveData<ChapterCount> = MutableLiveData<ChapterCount>()

    fun loadChapters(book: String) {
        FireflyRetriever.get().getChapters(book).observeForever { chapterCount ->
            (this.chapters as MutableLiveData<ChapterCount>).value = chapterCount
        }
    }
}