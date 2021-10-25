package com.pnuema.bible.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.pnuema.bible.android.R
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.adapters.BookSelectionRecyclerViewAdapter
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.fragments.viewModel.BooksViewModel

/**
 * A fragment representing a list of books to pick from.
 */
class BookSelectionFragment(private val listener: BCVSelectionListener) : Fragment(R.layout.fragment_book_list) {
    private val viewModel by viewModels<BooksViewModel>()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.books.observe(viewLifecycleOwner, Observer { model ->
            val adapter = BookSelectionRecyclerViewAdapter(model.books, listener)
            val recyclerView = view as RecyclerView
            recyclerView.adapter = adapter

            adapter.notifyDataSetChanged()

            if (CurrentSelected.book != CurrentSelected.DEFAULT_VALUE && model.books.isNotEmpty()) {
                for (book in model.books) {
                    if (book.getId() == CurrentSelected.book && recyclerView.layoutManager is LinearLayoutManager) {
                        (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(model.books.indexOf(book), recyclerView.height / 2)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        FirebaseAnalytics.getInstance(requireContext()).logEvent("BookSelectionFragment", null)

        if (isVisible) {
            viewModel.loadBooks()
        }
    }
}
