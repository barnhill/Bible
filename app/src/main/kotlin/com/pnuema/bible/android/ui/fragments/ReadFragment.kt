package com.pnuema.bible.android.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.google.firebase.analytics.FirebaseAnalytics
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.data.IBookProvider
import com.pnuema.bible.android.data.IVerseProvider
import com.pnuema.bible.android.data.IVersionProvider
import com.pnuema.bible.android.databinding.FragmentReadBinding
import com.pnuema.bible.android.retrievers.FireflyRetriever.Companion.get
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.CurrentSelected.chapter
import com.pnuema.bible.android.statics.CurrentSelected.verse
import com.pnuema.bible.android.statics.CurrentSelected.version
import com.pnuema.bible.android.ui.adapters.VersesAdapter
import com.pnuema.bible.android.ui.dialogs.BCVDialog
import com.pnuema.bible.android.ui.dialogs.NotifySelectionCompleted
import com.pnuema.bible.android.ui.dialogs.NotifyVersionSelectionCompleted
import com.pnuema.bible.android.ui.fragments.viewModel.ReadViewModel
import com.pnuema.bible.android.ui.utils.DialogUtils.showBookChapterVersePicker
import com.pnuema.bible.android.ui.utils.DialogUtils.showVersionsPicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * The reading pane fragment
 */
class ReadFragment : Fragment(R.layout.fragment_read) {
    private val viewModel by viewModels<ReadViewModel>()
    private val books: MutableList<IBook> = ArrayList()

    private var _binding: FragmentReadBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentReadBinding.bind(view)

        val recyclerView: RecyclerView = binding.versesRecyclerView
        val layoutManager = recyclerView.layoutManager ?: error("LayoutManager is required to be set on the RecyclerView in the layout XML")

        (activity as? AppCompatActivity ?: return).setSupportActionBar(binding.appBar.toolbar)

        val bookChapterView = binding.appBar.selectedBook
        val translationView = binding.appBar.selectedTranslation
        val verseBottomPanel = binding.versesBottomPanel
        viewModel.liveVersions.observe(viewLifecycleOwner) { iVersionProvider: IVersionProvider ->
            for (version in iVersionProvider.versions) {
                if (version.abbreviation == CurrentSelected.version) {
                    translationView.text = version.abbreviation.uppercase(Locale.getDefault())
                    break
                }
            }
        }
        viewModel.liveBook.observe(viewLifecycleOwner) { iBookProvider: IBookProvider ->
            books.clear()
            books.addAll(iBookProvider.books)
            setBookChapterText(bookChapterView, verseBottomPanel)
        }
        viewModel.liveVerses.observe(viewLifecycleOwner) { iVerseProvider: IVerseProvider ->
            if (recyclerView.adapter == null) {
                recyclerView.adapter = VersesAdapter().apply {
                    stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                    initVerses(iVerseProvider.verses)
                }
            } else {
                (recyclerView.adapter as VersesAdapter).updateVerses(iVerseProvider.verses)
            }

            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
                scrollToVerse(
                    verse,
                    layoutManager
                )
            }

            setBookChapterText(bookChapterView, verseBottomPanel)
        }

        setAppBarDisplay(bookChapterView, translationView, verseBottomPanel)
    }

    override fun onResume() {
        super.onResume()
        FirebaseAnalytics.getInstance(requireContext()).logEvent("ReadFragment", Bundle().apply {
            putInt(BCVDialog.BCV.BOOK.toString(), CurrentSelected.book)
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

    private fun setAppBarDisplay(bookChapterView: TextView, translationView: TextView, verseBottomPanel: TextView) {
        val fragmentActivity = activity
        if (!isAdded || fragmentActivity == null) {
            return
        }
        setBookChapterText(bookChapterView, verseBottomPanel)
        bookChapterView.setOnClickListener { showBookChapterVersePicker(fragmentActivity, BCVDialog.BCV.BOOK, object : NotifySelectionCompleted {
                override fun onSelectionPreloadChapter(book: Int, chapter: Int) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        get().getVerses(version, CurrentSelected.book.toString(), CurrentSelected.chapter.toString())
                    }
                }

                override fun onSelectionComplete(book: Int, chapter: Int, verse: Int) {
                    refresh()
                }
            })
        }
        translationView.setOnClickListener { showVersionsPicker(fragmentActivity, object : NotifyVersionSelectionCompleted {
                override fun onSelectionComplete(version: String) {
                    CurrentSelected.version = version
                    refresh()
                }
            })
        }
    }

    private fun setBookChapterText(bookChapterView: TextView, verseBottomPanel: TextView) {
        books.forEach { book ->
            if (book.getId() == CurrentSelected.book) {
                bookChapterView.text = getString(R.string.book_chapter_header_format, book.getName(), chapter)
                verseBottomPanel.text = getString(R.string.book_chapter_header_format, book.getName(), chapter)
            }
        }
    }
}