package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.firefly.Books
import com.pnuema.bible.android.retrievers.FireflyRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksViewModel: ViewModel() {
    private val _books: MutableLiveData<Books> = MutableLiveData<Books>()
    val books: LiveData<Books> = _books

    fun loadBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            _books.postValue(FireflyRetriever.get().getBooks())
        }
    }
}