package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.bible.android.data.IBookProvider
import com.pnuema.bible.android.data.firefly.Books
import com.pnuema.bible.android.retrievers.FireflyRetriever

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