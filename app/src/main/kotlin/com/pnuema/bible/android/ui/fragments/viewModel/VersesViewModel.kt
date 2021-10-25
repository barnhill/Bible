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
    private val _verses: MutableLiveData<VerseCount> = MutableLiveData()
    val verses: LiveData<VerseCount> = _verses

    fun loadVerses(version: String, book: String, chapter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _verses.postValue(FireflyRetriever.get().getVerseCount(version, book, chapter))
        }
    }
}