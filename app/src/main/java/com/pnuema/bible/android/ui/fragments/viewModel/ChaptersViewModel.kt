package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.firefly.ChapterCount
import com.pnuema.bible.android.retrievers.FireflyRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChaptersViewModel: ViewModel() {
    val chapters: LiveData<ChapterCount> = MutableLiveData<ChapterCount>()

    fun loadChapters(book: String) {
        viewModelScope.launch(Dispatchers.IO) {
            (chapters as MutableLiveData<ChapterCount>).postValue(FireflyRetriever.get().getChapters(book))
        }
    }
}