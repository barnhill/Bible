package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.firefly.ChapterCount
import com.pnuema.bible.android.repository.FireflyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChaptersViewModel(private val fireflyRepository: FireflyRepository = FireflyRepository()): ViewModel() {
    private val _chapters: MutableLiveData<ChapterCount> = MutableLiveData()
    val chapters: LiveData<ChapterCount> = _chapters

    fun loadChapters(book: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getChapters(book).collect { chapterCount  ->
                _chapters.postValue(chapterCount)
            }
        }
    }
}