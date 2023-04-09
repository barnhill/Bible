package com.pnuema.bible.android.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.MenuProvider
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
import com.pnuema.bible.android.ui.fragments.uiStates.ChaptersUiState
import com.pnuema.bible.android.ui.fragments.viewModel.ChaptersViewModel
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of chapter numbers to pick from.
 */
class ChapterSelectionFragment(private val listener: BCVSelectionListener) : Fragment(R.layout.fragment_number_list) {
    private val viewModel by viewModels<ChaptersViewModel>()
    private var _binding: FragmentNumberListBinding? = null
    private val binding: FragmentNumberListBinding get() = _binding!!

    companion object {
        fun newInstance(listener: BCVSelectionListener): ChapterSelectionFragment {
            return ChapterSelectionFragment(listener)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNumberListBinding.bind(view)
        binding.numberGridView.layoutManager = GridLayoutManager(context, 3)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.chapters
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        is ChaptersUiState.ChaptersState -> binding.numberGridView.adapter =
                            NumberSelectionAdapter(
                                state.viewState.chapterCount,
                                CurrentSelected.chapter
                            )
                            { number -> listener.onChapterSelected(number) }
                        ChaptersUiState.Idle -> Unit
                        ChaptersUiState.Loading -> Unit
                        ChaptersUiState.NotLoading -> Unit
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()

        FirebaseAnalytics.getInstance(requireContext()).logEvent("ChapterSelectionFragment", null)

        viewModel.loadChapters(CurrentSelected.version, CurrentSelected.book)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
