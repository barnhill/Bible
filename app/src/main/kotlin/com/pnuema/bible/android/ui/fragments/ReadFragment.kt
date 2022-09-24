package com.pnuema.bible.android.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.google.firebase.analytics.FirebaseAnalytics
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.FragmentReadBinding
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.CurrentSelected.book
import com.pnuema.bible.android.statics.CurrentSelected.chapter
import com.pnuema.bible.android.statics.CurrentSelected.verse
import com.pnuema.bible.android.ui.adapters.VersesAdapter
import com.pnuema.bible.android.ui.dialogs.BCVDialog
import com.pnuema.bible.android.ui.dialogs.NotifySelectionCompleted
import com.pnuema.bible.android.ui.dialogs.NotifyVersionSelectionCompleted
import com.pnuema.bible.android.ui.fragments.uiStates.ReadBookUiState
import com.pnuema.bible.android.ui.fragments.uiStates.ReadUiState
import com.pnuema.bible.android.ui.fragments.uiStates.VersionUiState
import com.pnuema.bible.android.ui.fragments.viewModel.ReadViewModel
import com.pnuema.bible.android.ui.utils.DialogUtils.showBookChapterVersePicker
import com.pnuema.bible.android.ui.utils.DialogUtils.showVersionsPicker
import com.pnuema.bible.android.ui.viewstates.BookViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * The reading pane fragment
 */
class ReadFragment : Fragment(R.layout.fragment_read) {
    private val viewModel by viewModels<ReadViewModel>()

    private var _binding: FragmentReadBinding? = null
    private val binding get() = _binding!!
    val adapter: VersesAdapter get() = binding.versesRecyclerView.adapter as VersesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReadBinding.bind(view)

        val recyclerView: RecyclerView = binding.versesRecyclerView
        val layoutManager = recyclerView.layoutManager ?: error("LayoutManager is required to be set on the RecyclerView in the layout XML")

        (activity as? AppCompatActivity ?: return).setSupportActionBar(binding.appBar.toolbar)

        setAppBarDisplay()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (requireActivity().supportFragmentManager.backStackEntryCount > 0) {
                    requireActivity().supportFragmentManager.popBackStack()
                } else {
                    requireActivity().finish()
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateVersions
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        VersionUiState.Idle -> Unit
                        is VersionUiState.Versions -> {
                            for (version in state.versions) {
                                if (version.abbreviation == CurrentSelected.version) {
                                    binding.appBar.selectedTranslation.text =
                                        version.abbreviation.uppercase(Locale.getDefault())
                                    break
                                }
                            }
                        }
                    }
                }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateBook
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        is ReadBookUiState.Books -> {
                            setBookChapterText(state.books)
                        }
                        ReadBookUiState.Idle -> Unit
                    }
                }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateVerses
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when(state) {
                        ReadUiState.Idle -> Unit
                        is ReadUiState.Verses -> {
                            if (recyclerView.adapter == null) {
                                recyclerView.adapter = VersesAdapter()
                            }

                            adapter.submitList(state.verses)

                            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                                scrollToVerse(
                                    verse,
                                    layoutManager
                                )
                            }
                        }
                    }
            }
        }
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun refresh() {
        viewModel.load()
    }

    private fun scrollToVerse(verse: Int, layoutManager: RecyclerView.LayoutManager) {
        if (verse <= 0) return //prevents scrolling offline which would crash
        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(requireActivity()) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }.apply {
            targetPosition = verse - 1
        }

        layoutManager.startSmoothScroll(smoothScroller)
    }

    private fun setAppBarDisplay() {
        val fragmentActivity = activity
        if (!isAdded || fragmentActivity == null) {
            return
        }

        binding.appBar.selectedBook.setOnClickListener { showBookChapterVersePicker(fragmentActivity, BCVDialog.BCV.BOOK, object : NotifySelectionCompleted {
                override fun onSelectionComplete(book: Int, chapter: Int, verse: Int) {
                    refresh()
                }
            })
        }
        binding.appBar.selectedTranslation.setOnClickListener { showVersionsPicker(fragmentActivity, object : NotifyVersionSelectionCompleted {
                override fun onSelectionComplete(version: String) {
                    CurrentSelected.version = version
                    refresh()
                }
            })
        }
    }

    private fun setBookChapterText(books: List<BookViewState>) {
        books.find{ it.id == book }?.let { book ->
            binding.appBar.selectedBook.text = getString(R.string.book_chapter_header_format, book.name, chapter)
            binding.versesBottomPanel.text = getString(R.string.book_chapter_header_format, book.name, chapter)
        }
    }
}