package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.IBookProvider
import com.pnuema.bible.android.data.IVerseProvider
import com.pnuema.bible.android.data.IVersionProvider
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.statics.CurrentSelected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ReadViewModel constructor(private val fireflyRepository: FireflyRepository = FireflyRepository()): ViewModel() {
    private val _liveVersions: MutableLiveData<IVersionProvider> = MutableLiveData()
    val liveVersions: LiveData<IVersionProvider> get() = _liveVersions

    private val _liveVerses: MutableLiveData<IVerseProvider> = MutableLiveData()
    val liveVerses: LiveData<IVerseProvider> get() = _liveVerses

    private val _liveBook: MutableLiveData<IBookProvider> = MutableLiveData()
    val liveBook: LiveData<IBookProvider> get() = _liveBook

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getVerses(CurrentSelected.version, CurrentSelected.book, CurrentSelected.chapter).collect { verses ->
                _liveVerses.postValue(verses)
            }
            fireflyRepository.getVersions().collect { versions ->
                _liveVersions.postValue(versions)
            }
            fireflyRepository.getBooks().collect { books ->
                _liveBook.postValue(books)
            }
        }
    }

    suspend fun search(query: String) {
        /*FireflyRetriever.get().searchVerses(query = query).verses.forEach {
            Log.w("Brad", "" + it)
        }*/
    }
}