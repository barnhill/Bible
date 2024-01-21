package com.pnuema.bible.android.ui.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.CurrentSelected.book
import com.pnuema.bible.android.statics.CurrentSelected.chapter
import com.pnuema.bible.android.statics.CurrentSelected.verse
import com.pnuema.bible.android.ui.bookchapterverse.BCVDialog
import com.pnuema.bible.android.ui.bookchapterverse.NotifySelectionCompleted
import com.pnuema.bible.android.ui.read.compose.ReadScreen
import com.pnuema.bible.android.ui.read.state.ReadBookUiState
import com.pnuema.bible.android.ui.read.state.ReadUiState
import com.pnuema.bible.android.ui.read.state.VersionUiState
import com.pnuema.bible.android.ui.read.state.VersionViewState
import com.pnuema.bible.android.ui.read.viewModel.ReadViewModel
import com.pnuema.bible.android.ui.utils.DialogUtils.showBookChapterVersePicker
import com.pnuema.bible.android.ui.utils.setContent
import com.pnuema.bible.android.ui.versionselection.ui.VersionSelectionDialog
import dagger.hilt.android.AndroidEntryPoint

/**
 * The reading pane fragment
 */
@AndroidEntryPoint
class ReadFragment : Fragment() {
    private val viewModel: ReadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = setContent {
        val books by viewModel.stateBook.collectAsStateWithLifecycle()
        val verses by viewModel.stateVerses.collectAsStateWithLifecycle()
        val version by viewModel.stateVersion.collectAsStateWithLifecycle()

        when (books) {
            is ReadBookUiState.Books -> {
                val model = books as ReadBookUiState.Books
                model.books.find{ it.id == book }?.let { book ->
                    ReadScreen(
                        book = book.name,
                        chapter = chapter.toString(),
                        verseToFocus = verse,
                        version = when(version) {
                            is VersionUiState.Idle -> VersionUiState.Version(version = VersionViewState("", "", ""))
                            is VersionUiState.Version -> version as VersionUiState.Version
                        },
                        verses = when(verses) {
                            is ReadUiState.Idle -> ReadUiState.Verses(listOf())
                            is ReadUiState.Verses -> verses as ReadUiState.Verses
                        },
                        onBookChapterClicked = {
                            setFragmentResultListener(VersionSelectionDialog.RESULT_KEY) { _, _ ->
                                refresh()
                            }
                            findNavController().navigate(
                                ReadFragmentDirections.actionReadFragmentToBCVDialog()
                            )
                            showBookChapterVersePicker(parentFragmentManager, BCVDialog.BCV.BOOK, object :
                                NotifySelectionCompleted {
                                override fun onSelectionComplete(book: Int, chapter: Int, verse: Int) {
                                    refresh()
                                }
                            })
                        },
                        onVersionClicked = {
                            setFragmentResultListener(VersionSelectionDialog.RESULT_KEY) { key, bundle ->
                                CurrentSelected.version = bundle.getString(key).toString()
                                refresh()
                            }
                            findNavController().navigate(
                                ReadFragmentDirections.actionReadFragmentToVersionSelectionDialog()
                            )
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