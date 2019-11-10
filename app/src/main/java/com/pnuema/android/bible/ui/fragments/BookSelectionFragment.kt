package com.pnuema.android.bible.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.data.IBook
import com.pnuema.android.bible.data.IBookProvider
import com.pnuema.android.bible.retrievers.FireflyRetriever
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.ui.adapters.BookSelectionRecyclerViewAdapter
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener
import java.util.*

/**
 * A fragment representing a list of books to pick from.
 */
class BookSelectionFragment : Fragment(), Observer {
    private lateinit var mListener: BCVSelectionListener
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: BookSelectionRecyclerViewAdapter
    private val mBooks = ArrayList<IBook>()
    private val mRetriever = FireflyRetriever()

    companion object {
        fun newInstance(listener: BCVSelectionListener): BookSelectionFragment {
            return BookSelectionFragment().setListener(listener)
        }
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    init {
        setHasOptionsMenu(true)
    }

    private fun setListener(listener: BCVSelectionListener): BookSelectionFragment {
        mListener = listener
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_book_list, container, false)

        mAdapter = BookSelectionRecyclerViewAdapter(mBooks, mListener)
        mRecyclerView = view as RecyclerView
        mRecyclerView.adapter = mAdapter

        return view
    }

    override fun onResume() {
        super.onResume()

        if (isMenuVisible && CurrentSelected.getBook() != null) {
            mRetriever.addObserver(this)
            mRetriever.getBooks()
        } else {
            mRetriever.deleteObservers()
        }
    }

    override fun onPause() {
        super.onPause()
        mRetriever.deleteObservers()
    }

    override fun update(observable: Observable, o: Any) {
        mBooks.clear()
        if (o is IBookProvider) {
            mBooks.addAll(o.books)
            mAdapter.notifyDataSetChanged()

            if (CurrentSelected.getBook() != null && mBooks.isNotEmpty()) {
                for (book in mBooks) {
                    if (book.id == CurrentSelected.getBook() && mRecyclerView.layoutManager is LinearLayoutManager) {
                        (mRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(mBooks.indexOf(book), mRecyclerView.height / 2)
                    }
                }
            }
        }
    }
}
