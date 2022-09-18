package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.firefly.Books
import com.pnuema.bible.android.repository.FireflyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BooksViewModel(private val fireflyRepository: FireflyRepository = FireflyRepository()): ViewModel() {
    private val _books: MutableLiveData<Books> = MutableLiveData<Books>()
    val books: LiveData<Books> = _books

    fun loadBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getBooks().collect { books ->
                _books.postValue(books)
            }
        }
    }
}