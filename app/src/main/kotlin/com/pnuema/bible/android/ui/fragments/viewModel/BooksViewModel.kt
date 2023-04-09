package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.ui.fragments.uiStates.BooksUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BooksViewModel(private val fireflyRepository: FireflyRepository = FireflyRepository()): ViewModel() {
    private val _books: MutableStateFlow<BooksUiState> = MutableStateFlow(BooksUiState.Idle)
    val books: StateFlow<BooksUiState> = _books

    fun loadBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            _books.update { BooksUiState.Loading }
            fireflyRepository.getBooks().collect { books ->
                _books.update{ BooksUiState.NotLoading }
                _books.update{ BooksUiState.BooksState(books.convertToViewStates()) }
            }
        }
    }
}