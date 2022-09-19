package com.pnuema.bible.android.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.FragmentBookListBinding
import com.pnuema.bible.android.databinding.FragmentNumberListBinding
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.adapters.BookSelectionRecyclerViewAdapter
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.fragments.uiStates.BooksUiState
import com.pnuema.bible.android.ui.fragments.viewModel.BooksViewModel
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of books to pick from.
 */
class BookSelectionFragment(private val listener: BCVSelectionListener) : Fragment(R.layout.fragment_book_list) {
    private val viewModel by viewModels<BooksViewModel>()
    private var _binding: FragmentBookListBinding? = null
    private val binding: FragmentBookListBinding get() = _binding!!
    private val adapter: BookSelectionRecyclerViewAdapter get() = binding.list.adapter as BookSelectionRecyclerViewAdapter

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

        _binding = FragmentBookListBinding.bind(view)

        binding.list.adapter = BookSelectionRecyclerViewAdapter(listener)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.books
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        is BooksUiState.BooksState -> {
                                adapter.submitList(state.viewStates)
                                for (book in state.viewStates) {
                                    if (book.id == CurrentSelected.book) {
                                        (binding.list.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                                            state.viewStates.indexOf(book),
                                            binding.list.height / 2
                                        )
                                    }
                                }
                        }
                        BooksUiState.Idle -> Unit
                        BooksUiState.Loading -> Unit
                        BooksUiState.NotLoading -> Unit
                    }
                }
        }

    }

    override fun onResume() {
        super.onResume()

        FirebaseAnalytics.getInstance(requireContext()).logEvent("BookSelectionFragment", null)

        if (isVisible) {
            viewModel.loadBooks()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
