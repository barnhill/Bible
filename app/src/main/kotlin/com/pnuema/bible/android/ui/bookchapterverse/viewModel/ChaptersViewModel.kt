package com.pnuema.bible.android.ui.bookchapterverse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.repository.BaseRepository
import com.pnuema.bible.android.ui.bookchapterverse.state.ChaptersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChaptersViewModel @Inject constructor(private val repository: BaseRepository): ViewModel() {
    private val _chapters: MutableStateFlow<ChaptersUiState> = MutableStateFlow(ChaptersUiState.Idle)
    val chapters: StateFlow<ChaptersUiState> = _chapters

    fun loadChapters(version: String, book: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _chapters.update { ChaptersUiState.Loading }
            repository.getChapters(version, book).collect { chapterCount  ->
                _chapters.update { ChaptersUiState.NotLoading }
                _chapters.update { ChaptersUiState.ChaptersState(chapterCount.convertToViewState()) }
            }
        }
    }
}