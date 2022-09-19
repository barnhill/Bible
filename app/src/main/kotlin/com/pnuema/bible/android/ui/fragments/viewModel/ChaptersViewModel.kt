package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.ui.fragments.uiStates.ChaptersUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChaptersViewModel(private val fireflyRepository: FireflyRepository = FireflyRepository()): ViewModel() {
    private val _chapters: MutableStateFlow<ChaptersUiState> = MutableStateFlow(ChaptersUiState.Idle)
    val chapters: StateFlow<ChaptersUiState> = _chapters

    fun loadChapters(version: String, book: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _chapters.value = ChaptersUiState.Loading
            fireflyRepository.getChapters(version, book).collect { chapterCount  ->
                _chapters.value = ChaptersUiState.NotLoading
                _chapters.value = ChaptersUiState.ChaptersState(chapterCount.convertToViewState())
            }
        }
    }
}