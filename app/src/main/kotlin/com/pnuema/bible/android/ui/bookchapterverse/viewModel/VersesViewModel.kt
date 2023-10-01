package com.pnuema.bible.android.ui.bookchapterverse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.ui.bookchapterverse.uiStates.VersesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VersesViewModel @Inject constructor(private val fireflyRepository: FireflyRepository): ViewModel() {
    private val _verses: MutableStateFlow<VersesUiState> = MutableStateFlow(VersesUiState.Idle)
    val verses: StateFlow<VersesUiState> = _verses

    fun loadVerses(version: String, book: Int, chapter: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _verses.update { VersesUiState.Loading }
            fireflyRepository.getVerseCount(version, book, chapter).collect { verseCount ->
                _verses.update { VersesUiState.NotLoading }
                _verses.update { VersesUiState.VersesState(verseCount.convertToViewState()) }
            }
        }
    }
}