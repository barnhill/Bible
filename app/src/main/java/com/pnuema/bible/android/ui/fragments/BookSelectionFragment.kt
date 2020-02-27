package com.pnuema.bible.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.adapters.BookSelectionRecyclerViewAdapter
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.fragments.viewModel.BooksViewModel

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

        viewModel = ViewModelProvider(this).get(BooksViewModel::class.java)

        viewModel.books.observe(viewLifecycleOwner, Observer { model ->
            mAdapter = BookSelectionRecyclerViewAdapter(model.books, listener)
            mRecyclerView = view as RecyclerView
            mRecyclerView.adapter = mAdapter

            mAdapter.notifyDataSetChanged()

            if (CurrentSelected.book != null && model.books.isNotEmpty()) {
                for (book in model.books) {
                    if (book.getId() == CurrentSelected.book && mRecyclerView.layoutManager is LinearLayoutManager) {
                        (mRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(model.books.indexOf(book), mRecyclerView.height / 2)
                    }
                }
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()

        if (isVisible) {
            viewModel.loadBooks()
        }
    }
}
