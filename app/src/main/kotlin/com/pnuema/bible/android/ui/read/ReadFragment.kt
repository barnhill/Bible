package com.pnuema.bible.android.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.analytics.FirebaseAnalytics
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.CurrentSelected.book
import com.pnuema.bible.android.statics.CurrentSelected.chapter
import com.pnuema.bible.android.statics.CurrentSelected.verse
import com.pnuema.bible.android.ui.bookchapterverse.BCVDialog
import com.pnuema.bible.android.ui.bookchapterverse.NotifySelectionCompleted
import com.pnuema.bible.android.ui.dialogs.NotifyVersionSelectionCompleted
import com.pnuema.bible.android.ui.read.compose.ReadScreen
import com.pnuema.bible.android.ui.read.state.ReadBookUiState
import com.pnuema.bible.android.ui.read.state.ReadUiState
import com.pnuema.bible.android.ui.read.state.VersionUiState
import com.pnuema.bible.android.ui.read.viewModel.ReadViewModel
import com.pnuema.bible.android.ui.utils.DialogUtils
import com.pnuema.bible.android.ui.utils.DialogUtils.showBookChapterVersePicker
import com.pnuema.bible.android.ui.utils.setContent
import dagger.hilt.android.AndroidEntryPoint

/**
 * The reading pane fragment
 */
@AndroidEntryPoint
class ReadFragment : Fragment() {
    private val viewModel by viewModels<ReadViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = setContent {
        val books by viewModel.stateBook.collectAsStateWithLifecycle()
        val verses by viewModel.stateVerses.collectAsStateWithLifecycle()
        val versions by viewModel.stateVersions.collectAsStateWithLifecycle()

        when (books) {
            is ReadBookUiState.Books -> {
                val model = books as ReadBookUiState.Books
                model.books.find{ it.id == book }?.let { book ->
                    ReadScreen(
                        book = book.name,
                        chapter = chapter.toString(),
                        verseToFocus = verse,
                        versionAbbreviation = when(versions) {
                            is VersionUiState.Idle -> CurrentSelected.version
                            is VersionUiState.Versions -> CurrentSelected.version
                        },
                        verses = when(verses) {
                            is ReadUiState.Idle -> ReadUiState.Verses(listOf())
                            is ReadUiState.Verses -> verses as ReadUiState.Verses
                        },
                        onBookChapterClicked = {
                            showBookChapterVersePicker(parentFragmentManager, BCVDialog.BCV.BOOK, object :
                                NotifySelectionCompleted {
                                override fun onSelectionComplete(book: Int, chapter: Int, verse: Int) {
                                    refresh()
                                }
                            })
                        },
                        onVersionClicked = {
                            DialogUtils.showVersionsPicker(parentFragmentManager, object :
                                NotifyVersionSelectionCompleted {
                                override fun onSelectionComplete(version: String) {
                                    CurrentSelected.version = version
                                    refresh()
                                }
                            })
                        }
                    )
                }
            }
            is ReadBookUiState.Idle -> Unit
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (requireActivity().supportFragmentManager.backStackEntryCount > 0) {
                    requireActivity().supportFragmentManager.popBackStack()
                } else {
                    requireActivity().finish()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        FirebaseAnalytics.getInstance(requireContext()).logEvent("ReadFragment", Bundle().apply {
            putInt(BCVDialog.BCV.BOOK.toString(), book)
            putInt(BCVDialog.BCV.CHAPTER.toString(), chapter)
            putInt(BCVDialog.BCV.VERSE.toString(), verse)
        })
        refresh()
    }

    fun refresh() {
        viewModel.load()
    }
}