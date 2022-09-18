package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.firefly.VerseCount
import com.pnuema.bible.android.repository.FireflyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class VersesViewModel(private val fireflyRepository: FireflyRepository = FireflyRepository()): ViewModel() {
    private val _verses: MutableLiveData<VerseCount> = MutableLiveData()
    val verses: LiveData<VerseCount> = _verses

    fun loadVerses(version: String, book: Int, chapter: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getVerseCount(version, book, chapter).collect { verseCount ->
                _verses.postValue(verseCount)
            }
        }
    }
}