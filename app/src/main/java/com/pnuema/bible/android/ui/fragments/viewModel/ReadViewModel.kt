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
    val liveVersions: LiveData<IVersionProvider> = MutableLiveData()
    val liveVerses: LiveData<IVerseProvider> = MutableLiveData()
    val liveBook: LiveData<IBookProvider> = MutableLiveData()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            if (CurrentSelected.chapter != null) {
                (liveVerses as MutableLiveData<IVerseProvider>).postValue(FireflyRetriever.get().getVerses(CurrentSelected.version, CurrentSelected.book.toString(), CurrentSelected.chapter.toString()))
            }

            (liveVersions as MutableLiveData<IVersionProvider>).postValue(FireflyRetriever.get().getVersions())

            if (CurrentSelected.book != null) {
                (liveBook as MutableLiveData<IBookProvider>).postValue(FireflyRetriever.get().getBooks())
            }
        }
    }
}