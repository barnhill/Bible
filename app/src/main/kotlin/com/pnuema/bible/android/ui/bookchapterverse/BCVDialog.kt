package com.pnuema.bible.android.ui.bookchapterverse

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.CurrentSelected.book
import com.pnuema.bible.android.statics.CurrentSelected.chapter
import com.pnuema.bible.android.statics.CurrentSelected.verse
import com.pnuema.bible.android.statics.CurrentSelected.version
import com.pnuema.bible.android.ui.bookchapterverse.compose.BCVDialogScreen
import com.pnuema.bible.android.ui.bookchapterverse.viewModel.BooksViewModel
import com.pnuema.bible.android.ui.bookchapterverse.viewModel.ChaptersViewModel
import com.pnuema.bible.android.ui.bookchapterverse.viewModel.VersesViewModel
import com.pnuema.bible.android.ui.utils.setContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Book/Chapter/Verse selection dialog
 */
@AndroidEntryPoint
class BCVDialog : Fragment(), BCVSelectionListener {
    private var listener: NotifySelectionCompleted? = null

    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().supportFragmentManager.popBackStack()
            remove()
        }
    }

    enum class BCV(val value: Int) {
        BOOK(0), CHAPTER(1), VERSE(2)
    }

    private val _selectedTab: MutableStateFlow<BCV> = MutableStateFlow(BCV.BOOK)
    private val selectedTab: StateFlow<BCV> = _selectedTab.asStateFlow()

    private val booksViewModel by viewModels<BooksViewModel>()
    private val chaptersViewModel by viewModels<ChaptersViewModel>()
    private val versesViewModel by viewModels<VersesViewModel>()

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = setContent {
        val state by selectedTab.collectAsStateWithLifecycle()
        val bookUiState by booksViewModel.books.collectAsStateWithLifecycle()
        val chapterUiState by chaptersViewModel.chapters.collectAsStateWithLifecycle()
        val verseUiState by versesViewModel.verses.collectAsStateWithLifecycle()
        val pageCount = remember { BCV.entries.toTypedArray().count() }
        val pagerState = rememberPagerState(
            initialPage = BCV.BOOK.value,
            initialPageOffsetFraction = 0f,
            pageCount = { pageCount }
        )
        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch {
            pagerState.animateScrollToPage(state.value)
        }

        BCVDialogScreen(
            pagerState = pagerState,
            booksUiState = bookUiState,
            chaptersUiState = chapterUiState,
            versesUiState = verseUiState,
            listener = this,
            onBackPressed = {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        )
    }.apply {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)
    }

    override fun onStart() {
        super.onStart()
        booksViewModel.loadBooks()
        chaptersViewModel.loadChapters(version, book)
        versesViewModel.loadVerses(version, book, chapter)
    }

    override fun onBookSelected(book: Int) {
        if (CurrentSelected.book != book) {
            CurrentSelected.book = book
            CurrentSelected.clearChapter()
            CurrentSelected.clearVerse()
        }
        chaptersViewModel.loadChapters(version, CurrentSelected.book)
        gotoTab(BCV.CHAPTER)
    }

    override fun onChapterSelected(chapter: Int) {
        if (CurrentSelected.chapter != chapter) {
            CurrentSelected.chapter = chapter
            CurrentSelected.clearVerse()
        }
        versesViewModel.loadVerses(version, book, CurrentSelected.chapter)
        gotoTab(BCV.VERSE)
    }

    override fun onVerseSelected(verse: Int) {
        CurrentSelected.verse = verse
        refresh()
    }

    private fun gotoTab(tab: BCV) {
        viewLifecycleOwner.lifecycleScope.launch {
            _selectedTab.emit(tab)
        }
    }

    override fun refresh() {
        listener?.onSelectionComplete(book, chapter, verse)
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setListener(listener: NotifySelectionCompleted) {
        this.listener = listener
    }
}