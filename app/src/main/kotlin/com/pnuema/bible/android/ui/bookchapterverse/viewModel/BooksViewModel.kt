package com.pnuema.bible.android.ui.bookchapterverse.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.repository.BaseRepository
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.bookchapterverse.state.BooksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val repository: BaseRepository): ViewModel() {
    private val _books: MutableStateFlow<BooksUiState> = MutableStateFlow(BooksUiState.Idle)
    val books: StateFlow<BooksUiState> = _books

    fun loadBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            _books.update { BooksUiState.Loading }
            repository.getBooks(CurrentSelected.version).collect { books ->
                _books.update { BooksUiState.NotLoading }
                _books.update { BooksUiState.BooksState(books.convertToViewStates()) }
            }
        }
    }
}