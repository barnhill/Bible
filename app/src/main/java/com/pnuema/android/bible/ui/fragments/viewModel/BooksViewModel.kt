package com.pnuema.android.bible.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.android.bible.data.IBookProvider
import com.pnuema.android.bible.data.firefly.Books
import com.pnuema.android.bible.retrievers.FireflyRetriever

class BooksViewModel: ViewModel() {
    val books: LiveData<Books> = MutableLiveData<Books>()

    fun loadBooks() {
        FireflyRetriever.get().getBooks().observeForever { books ->
            if (books is IBookProvider) {
                (this.books as MutableLiveData<Books>).value = books
            }
        }
    }
}