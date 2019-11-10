package com.pnuema.android.bible.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.ui.adapters.BookSelectionRecyclerViewAdapter
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener
import com.pnuema.android.bible.ui.fragments.viewModel.BooksViewModel

/**
 * A fragment representing a list of books to pick from.
 */
class BookSelectionFragment(private val listener: BCVSelectionListener) : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: BookSelectionRecyclerViewAdapter
    private lateinit var viewModel: BooksViewModel

    companion object {
        fun newInstance(listener: BCVSelectionListener): BookSelectionFragment {
            return BookSelectionFragment(listener)
        }
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_book_list, container, false)

        viewModel = ViewModelProviders.of(this).get(BooksViewModel::class.java)

        viewModel.books.observe(viewLifecycleOwner, Observer { model ->
            mAdapter = BookSelectionRecyclerViewAdapter(model.books, listener)
            mRecyclerView = view as RecyclerView
            mRecyclerView.adapter = mAdapter

            mAdapter.notifyDataSetChanged()

            if (CurrentSelected.getBook() != null && model.books.isNotEmpty()) {
                for (book in model.books) {
                    if (book.id == CurrentSelected.getBook() && mRecyclerView.layoutManager is LinearLayoutManager) {
                        (mRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(model.books.indexOf(book), mRecyclerView.height / 2)
                    }
                }
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()

        if (isMenuVisible) {
            viewModel.loadBooks()
        }
    }
}
