package com.pnuema.bible.android.ui.bookchapterverse.bookselection.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.bookchapterverse.bookselection.state.BooksUiState
import com.pnuema.bible.android.ui.bookchapterverse.bookselection.state.BookViewState
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import kotlinx.coroutines.launch

@Composable
fun BookSelectionScreen(
    books: BooksUiState,
    listener: BCVSelectionListener,
) {
    BibleTheme {
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(
            state = listState,
            content = {
                when (books) {
                    is BooksUiState.BooksState -> {
                        val booksState = books as BooksUiState.BooksState
                        items(booksState.viewStates) {
                            BookItem(book = it, onClick = {
                                listener.onBookSelected(it.id)
                            })
                        }

                        val selectedIndex = booksState.viewStates.indexOfFirst { it.id == CurrentSelected.book }
                        coroutineScope.launch {
                            if (listState.isScrollInProgress) return@launch
                            listState.animateScrollToItem(selectedIndex)
                        }
                    }

                    is BooksUiState.Idle -> Unit
                    is BooksUiState.Loading -> Unit
                    is BooksUiState.NotLoading -> Unit
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookSelectionScreen_Preview() {
    BibleTheme {
        BookSelectionScreen(
            books = BooksUiState.BooksState(
                viewStates = listOf(
                    BookViewState(1, "Genesis", "Gen"),
                    BookViewState(2, "Exodus", "Ex"),
                    BookViewState(3, "Leviticus", "Lev"),
                    BookViewState(4, "Deuteronomy", "Deu")
                )
            ),
            listener = object : BCVSelectionListener {
                override fun onBookSelected(book: Int) {}

                override fun onChapterSelected(chapter: Int) {}

                override fun onVerseSelected(verse: Int) {}

                override fun refresh() {}
            }
        )
    }
}