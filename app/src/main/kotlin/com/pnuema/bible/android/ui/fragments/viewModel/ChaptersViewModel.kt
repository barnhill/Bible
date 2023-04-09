package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.ui.fragments.uiStates.ChaptersUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChaptersViewModel(private val fireflyRepository: FireflyRepository = FireflyRepository()): ViewModel() {
    private val _chapters: MutableStateFlow<ChaptersUiState> = MutableStateFlow(ChaptersUiState.Idle)
    val chapters: StateFlow<ChaptersUiState> = _chapters

    fun loadChapters(version: String, book: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _chapters.update { ChaptersUiState.Loading }
            fireflyRepository.getChapters(version, book).collect { chapterCount  ->
                _chapters.update { ChaptersUiState.NotLoading }
                _chapters.update { ChaptersUiState.ChaptersState(chapterCount.convertToViewState()) }
            }
        }
    }
}