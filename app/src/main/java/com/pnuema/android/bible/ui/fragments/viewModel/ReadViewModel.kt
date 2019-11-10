package com.pnuema.android.bible.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.bible.data.IBookProvider
import com.pnuema.android.bible.data.IVerseProvider
import com.pnuema.android.bible.data.IVersionProvider
import com.pnuema.android.bible.retrievers.FireflyRetriever
import com.pnuema.android.bible.statics.CurrentSelected

class ReadViewModel: ViewModel() {
    val liveVersions: LiveData<IVersionProvider> = MutableLiveData()
    val liveVerses: LiveData<IVerseProvider> = MutableLiveData()
    val liveBook: LiveData<IBookProvider> = MutableLiveData()

    fun load() {
        if (CurrentSelected.getChapter() != null) {
            FireflyRetriever.get().getVerses(CurrentSelected.getVersion(), CurrentSelected.getBook().toString(), CurrentSelected.getChapter().toString()).observeForever {
                (liveVerses as MutableLiveData<IVerseProvider>).value = it
            }
        }

        if (CurrentSelected.getVersion() != null) {
            FireflyRetriever.get().getVersions().observeForever {
                (liveVersions as MutableLiveData<IVersionProvider>).value = it
            }
        }

        if (CurrentSelected.getBook() != null) {
            FireflyRetriever.get().getBooks().observeForever {
                (liveBook as MutableLiveData<IBookProvider>).value = it
            }
        }
    }
}