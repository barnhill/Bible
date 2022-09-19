package com.pnuema.bible.android.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.FragmentNumberListBinding
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.adapters.NumberSelectionAdapter
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.fragments.uiStates.VersesUiState
import com.pnuema.bible.android.ui.fragments.viewModel.VersesViewModel
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of verse numbers to pick from.
 */
class VerseSelectionFragment(private val listener: BCVSelectionListener) : Fragment(R.layout.fragment_number_list) {
    private val viewModel by viewModels<VersesViewModel>()
    private var _binding: FragmentNumberListBinding? = null
    private val binding: FragmentNumberListBinding get() = _binding!!

    companion object {
        fun newInstance(listener: BCVSelectionListener): VerseSelectionFragment {
            return VerseSelectionFragment(listener)
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

        _binding = FragmentNumberListBinding.bind(view)
        binding.numberGridView.layoutManager = GridLayoutManager(context, 3)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.verses
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        VersesUiState.Idle -> Unit
                        VersesUiState.Loading -> Unit
                        VersesUiState.NotLoading -> Unit
                        is VersesUiState.VersesState -> binding.numberGridView.adapter = NumberSelectionAdapter(
                            state.viewState.verseCount,
                            CurrentSelected.verse
                        ) { number -> listener.onVerseSelected(number) }
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()

        FirebaseAnalytics.getInstance(requireContext()).logEvent("VerseSelectionFragment", null)

        viewModel.loadVerses(CurrentSelected.version, CurrentSelected.book, CurrentSelected.chapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
