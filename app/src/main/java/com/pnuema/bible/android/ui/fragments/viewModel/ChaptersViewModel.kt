package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.bible.android.data.firefly.ChapterCount
import com.pnuema.bible.android.retrievers.FireflyRetriever

class ChaptersViewModel: ViewModel() {
    val chapters: LiveData<ChapterCount> = MutableLiveData<ChapterCount>()

    fun loadChapters(book: String) {
        FireflyRetriever.get().getChapters(book).observeForever { chapterCount ->
            (this.chapters as MutableLiveData<ChapterCount>).value = chapterCount
        }
    }
}