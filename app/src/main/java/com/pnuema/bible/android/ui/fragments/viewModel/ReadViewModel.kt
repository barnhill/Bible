package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.bible.android.data.IBookProvider
import com.pnuema.bible.android.data.IVerseProvider
import com.pnuema.bible.android.data.IVersionProvider
import com.pnuema.bible.android.retrievers.FireflyRetriever
import com.pnuema.bible.android.statics.CurrentSelected

class ReadViewModel: ViewModel() {
    val liveVersions: LiveData<IVersionProvider> = MutableLiveData()
    val liveVerses: LiveData<IVerseProvider> = MutableLiveData()
    val liveBook: LiveData<IBookProvider> = MutableLiveData()

    fun load() {
        if (CurrentSelected.chapter != null) {
            FireflyRetriever.get().getVerses(CurrentSelected.version, CurrentSelected.book.toString(), CurrentSelected.chapter.toString()).observeForever {
                (liveVerses as MutableLiveData<IVerseProvider>).value = it
            }
        }

        FireflyRetriever.get().getVersions().observeForever {
            (liveVersions as MutableLiveData<IVersionProvider>).value = it
        }

        if (CurrentSelected.book != null) {
            FireflyRetriever.get().getBooks().observeForever {
                (liveBook as MutableLiveData<IBookProvider>).value = it
            }
        }
    }
}