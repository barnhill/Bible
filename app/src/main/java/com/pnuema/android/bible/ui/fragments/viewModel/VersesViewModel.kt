package com.pnuema.android.bible.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.bible.data.firefly.VerseCount
import com.pnuema.android.bible.retrievers.FireflyRetriever

class VersesViewModel: ViewModel() {
    val verses: LiveData<VerseCount> = MutableLiveData<VerseCount>()

    fun loadVerses(version: String, book: String, chapter: String) {
        FireflyRetriever.get().getVerseCount(version, book, chapter).observeForever { verseCount ->
            (this.verses as MutableLiveData<VerseCount>).value = verseCount
        }
    }
}