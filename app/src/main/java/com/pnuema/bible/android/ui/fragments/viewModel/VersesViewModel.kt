package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.firefly.VerseCount
import com.pnuema.bible.android.retrievers.FireflyRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VersesViewModel: ViewModel() {
    val verses: LiveData<VerseCount> = MutableLiveData<VerseCount>()

    fun loadVerses(version: String, book: String, chapter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            (verses as MutableLiveData<VerseCount>).postValue(FireflyRetriever.get().getVerseCount(version, book, chapter))
        }
    }
}