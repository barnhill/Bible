package com.pnuema.bible.android.ui.bookchapterverse.bookselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.analytics.FirebaseAnalytics
import com.pnuema.bible.android.ui.bookchapterverse.bookselection.compose.BookSelectionScreen
import com.pnuema.bible.android.ui.bookchapterverse.bookselection.viewModel.BooksViewModel
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.utils.setContent
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of books to pick from.
 */
@AndroidEntryPoint
class BookSelectionFragment(private val listener: BCVSelectionListener) : Fragment() {
    private val viewModel by viewModels<BooksViewModel>()

    companion object {
        fun newInstance(listener: BCVSelectionListener): BookSelectionFragment {
            return BookSelectionFragment(listener)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = setContent {
        val books by viewModel.books.collectAsStateWithLifecycle()
        BookSelectionScreen(
            books = books,
            listener = listener
        )
    }

    override fun onResume() {
        super.onResume()

        FirebaseAnalytics.getInstance(requireContext()).logEvent("BookSelectionFragment", null)

        if (isVisible) {
            viewModel.loadBooks()
        }
    }
}
