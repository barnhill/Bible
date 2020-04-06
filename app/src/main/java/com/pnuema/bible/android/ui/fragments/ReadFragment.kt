package com.pnuema.bible.android.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.data.IBookProvider
import com.pnuema.bible.android.data.IVerseProvider
import com.pnuema.bible.android.data.IVersionProvider
import com.pnuema.bible.android.retrievers.FireflyRetriever.Companion.get
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.CurrentSelected.DEFAULT_VALUE
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * The reading pane fragment
 */
class ReadFragment : Fragment(), CoroutineScope {
    private lateinit var viewModel: ReadViewModel
    private lateinit var bookChapterView: TextView
    private lateinit var translationView: TextView
    private lateinit var verseBottomPanel: TextView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var adapter: VersesAdapter
    private val books: MutableList<IBook> = ArrayList()

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    companion object {
        /**
         * Factory method to create the fragment
         * @return A new instance of fragment ReadFragment.
         */
        fun newInstance(): ReadFragment {
            return ReadFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_read, container)
        val recyclerView: RecyclerView = view.findViewById(R.id.verses_recycler_view)
        layoutManager = recyclerView.layoutManager?:throw IllegalStateException("LayoutManager is required to be set on the RecyclerView in the layout XML")
        adapter = VersesAdapter()
        recyclerView.adapter = adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = activity ?: return
        bookChapterView = activity.findViewById(R.id.selected_book)
        translationView = activity.findViewById(R.id.selected_translation)
        verseBottomPanel = activity.findViewById(R.id.verses_bottom_panel)
        viewModel = ViewModelProvider(this).get(ReadViewModel::class.java)
        viewModel.liveVersions.observe(viewLifecycleOwner, Observer { iVersionProvider: IVersionProvider ->
            val versions = iVersionProvider.versions
            for (version in versions) {
                if (version.abbreviation == CurrentSelected.version) {
                    translationView.text = version.abbreviation.toUpperCase(Locale.getDefault())
                    break
                }
            }
        })
        viewModel.liveBook.observe(viewLifecycleOwner, Observer { iBookProvider: IBookProvider ->
            books.clear()
            books.addAll(iBookProvider.books)
            setBookChapterText()
        })
        viewModel.liveVerses.observe(viewLifecycleOwner, Observer { iVerseProvider: IVerseProvider ->
            adapter.updateVerses(iVerseProvider.verses)
            Handler().post { scrollToVerse(verse) }
            setBookChapterText()
        })

        setAppBarDisplay()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    fun refresh() {
        viewModel.load()
    }

    private fun scrollToVerse(verse: Int) {
        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(Objects.requireNonNull(activity)) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = verse - 1
        layoutManager.startSmoothScroll(smoothScroller)
    }

    private fun setAppBarDisplay() {
        val fragmentActivity = activity
        if (!isAdded || fragmentActivity == null) {
            return
        }
        setBookChapterText()
        bookChapterView.setOnClickListener { showBookChapterVersePicker(fragmentActivity, BCVDialog.BCV.BOOK, object : NotifySelectionCompleted {
                override fun onSelectionPreloadChapter(book: Int, chapter: Int) {
                    launch {
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

    private fun setBookChapterText() {
        for (book in books) {
            if (CurrentSelected.book != DEFAULT_VALUE && book.getId() == CurrentSelected.book) {
                bookChapterView.text = getString(R.string.book_chapter_header_format, book.getName(), chapter)
                verseBottomPanel.text = getString(R.string.book_chapter_header_format, book.getName(), chapter)
            }
        }
    }
}