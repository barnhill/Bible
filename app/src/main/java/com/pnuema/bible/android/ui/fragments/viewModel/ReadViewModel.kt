package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.IBookProvider
import com.pnuema.bible.android.data.IVerseProvider
import com.pnuema.bible.android.data.IVersionProvider
import com.pnuema.bible.android.retrievers.FireflyRetriever
import com.pnuema.bible.android.statics.CurrentSelected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadViewModel: ViewModel() {
    private val _liveVersions: MutableLiveData<IVersionProvider> = MutableLiveData()
    val liveVersions: LiveData<IVersionProvider>
        get() = _liveVersions

    private val _liveVerses: MutableLiveData<IVerseProvider> = MutableLiveData()
    val liveVerses: LiveData<IVerseProvider>
        get() = _liveVerses

    private val _liveBook: MutableLiveData<IBookProvider> = MutableLiveData()
    val liveBook: LiveData<IBookProvider>
        get() = _liveBook

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            if (CurrentSelected.chapter != CurrentSelected.DEFAULT_VALUE) {
                _liveVerses.postValue(FireflyRetriever.get().getVerses(CurrentSelected.version, CurrentSelected.book.toString(), CurrentSelected.chapter.toString()))
            }

            _liveVersions.postValue(FireflyRetriever.get().getVersions())

            if (CurrentSelected.book != CurrentSelected.DEFAULT_VALUE) {
                _liveBook.postValue(FireflyRetriever.get().getBooks())
            }
        }
    }
}