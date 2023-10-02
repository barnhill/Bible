package com.pnuema.bible.android.ui.bookchapterverse.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.R
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.bookchapterverse.BCVDialog
import com.pnuema.bible.android.ui.bookchapterverse.BCVSelectionListener
import com.pnuema.bible.android.ui.bookchapterverse.state.BookViewState
import com.pnuema.bible.android.ui.bookchapterverse.state.BooksUiState
import com.pnuema.bible.android.ui.bookchapterverse.state.ChapterCountViewState
import com.pnuema.bible.android.ui.bookchapterverse.state.ChaptersUiState
import com.pnuema.bible.android.ui.bookchapterverse.state.VerseCountViewState
import com.pnuema.bible.android.ui.bookchapterverse.state.VersesUiState
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BCVDialogScreen(
    pagerState: PagerState,
    booksUiState: BooksUiState,
    chaptersUiState: ChaptersUiState,
    versesUiState: VersesUiState,
    listener: BCVSelectionListener,
) {
    val coroutineScope = rememberCoroutineScope()

    BibleTheme {
        Column {
            TabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
                Box(
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            pagerState.scrollToPage(BCVDialog.BCV.BOOK.value)
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                            .padding(vertical = 12.dp),
                        text = stringResource(R.string.book).uppercase(Locale.getDefault()),
                        style = if (pagerState.currentPage == BCVDialog.BCV.BOOK.value) BibleTheme.typography.body2 else BibleTheme.typography.body1,
                        color = if (pagerState.currentPage == BCVDialog.BCV.BOOK.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
                Box(
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            pagerState.scrollToPage(BCVDialog.BCV.CHAPTER.value)
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                            .padding(vertical = 12.dp),
                        text = stringResource(R.string.chapter).uppercase(Locale.getDefault()),
                        style = if (pagerState.currentPage == BCVDialog.BCV.CHAPTER.value) BibleTheme.typography.body2 else BibleTheme.typography.body1,
                        color = if (pagerState.currentPage == BCVDialog.BCV.CHAPTER.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
                Box(
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            pagerState.scrollToPage(BCVDialog.BCV.VERSE.value)
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                            .padding(vertical = 12.dp),
                        text = stringResource(R.string.verse).uppercase(Locale.getDefault()),
                        style = if (pagerState.currentPage == BCVDialog.BCV.VERSE.value) BibleTheme.typography.body2 else BibleTheme.typography.body1,
                        color = if (pagerState.currentPage == BCVDialog.BCV.VERSE.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            HorizontalPager(state = pagerState) {
                when(it) {
                    BCVDialog.BCV.BOOK.value -> BookSelectionScreen(books = booksUiState, onClick = { b -> listener.onBookSelected(b) })
                    BCVDialog.BCV.CHAPTER.value -> ChapterSelectionScreen(chapters = chaptersUiState, onClicked = { c -> listener.onChapterSelected(c) })
                    BCVDialog.BCV.VERSE.value -> VerseSelectionScreen(verses = versesUiState, onClicked = { v -> listener.onVerseSelected(v) })
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun BCVDialogScreen_BookSelected_Preview() {
    BCVDialogScreen(
        pagerState = rememberPagerState(
            initialPage = BCVDialog.BCV.BOOK.value,
            initialPageOffsetFraction = 0f,
            pageCount = { BCVDialog.BCV.values().count() }
        ),
        booksUiState = BooksUiState.BooksState(viewStates = listOf(
            BookViewState(1, "Genesis", "Gen"),
            BookViewState(2, "Exodus", "Ex"),
            BookViewState(3, "Leviticus", "Lev"),
            BookViewState(4, "Deuteronomy", "Deu")
        )),
        chaptersUiState = ChaptersUiState.ChaptersState(
            viewState = ChapterCountViewState(chapterCount = 15)
        ),
        versesUiState = VersesUiState.VersesState(
            viewState = VerseCountViewState(15)
        ),
        listener = object : BCVSelectionListener {
            override fun onBookSelected(book: Int) {}
            override fun onChapterSelected(chapter: Int) {}
            override fun onVerseSelected(verse: Int) {}
            override fun refresh() {}
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun BCVDialogScreen_ChapterSelected_Preview() {
    BCVDialogScreen(
        pagerState = rememberPagerState(
            initialPage = BCVDialog.BCV.CHAPTER.value,
            initialPageOffsetFraction = 0f,
            pageCount = { BCVDialog.BCV.values().count() }
        ),
        booksUiState = BooksUiState.BooksState(viewStates = listOf(
            BookViewState(1, "Genesis", "Gen"),
            BookViewState(2, "Exodus", "Ex"),
            BookViewState(3, "Leviticus", "Lev"),
            BookViewState(4, "Deuteronomy", "Deu")
        )),
        chaptersUiState = ChaptersUiState.ChaptersState(
            viewState = ChapterCountViewState(chapterCount = 15)
        ),
        versesUiState = VersesUiState.VersesState(
            viewState = VerseCountViewState(15)
        ),
        listener = object : BCVSelectionListener {
            override fun onBookSelected(book: Int) {}
            override fun onChapterSelected(chapter: Int) {}
            override fun onVerseSelected(verse: Int) {}
            override fun refresh() {}
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun BCVDialogScreen_VerseSelected_Preview() {
    BCVDialogScreen(
        pagerState = rememberPagerState(
            initialPage = BCVDialog.BCV.VERSE.value,
            initialPageOffsetFraction = 0f,
            pageCount = { BCVDialog.BCV.values().count() }
        ),
        booksUiState = BooksUiState.BooksState(viewStates = listOf(
            BookViewState(1, "Genesis", "Gen"),
            BookViewState(2, "Exodus", "Ex"),
            BookViewState(3, "Leviticus", "Lev"),
            BookViewState(4, "Deuteronomy", "Deu")
        )),
        chaptersUiState = ChaptersUiState.ChaptersState(
            viewState = ChapterCountViewState(chapterCount = 15)
        ),
        versesUiState = VersesUiState.VersesState(
            viewState = VerseCountViewState(15)
        ),
        listener = object : BCVSelectionListener {
            override fun onBookSelected(book: Int) {}
            override fun onChapterSelected(chapter: Int) {}
            override fun onVerseSelected(verse: Int) {}
            override fun refresh() {}
        }
    )
}