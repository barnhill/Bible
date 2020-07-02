package com.pnuema.bible.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.pnuema.bible.android.R
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.adapters.NumberSelectionAdapter
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.dialogs.NumberSelectionListener
import com.pnuema.bible.android.ui.fragments.viewModel.ChaptersViewModel

/**
 * A fragment representing a list of chapter numbers to pick from.
 */
class ChapterSelectionFragment(private val listener: BCVSelectionListener) : Fragment(R.layout.fragment_number_list), NumberSelectionListener {
    private val viewModel by viewModels<ChaptersViewModel>()

    companion object {
        fun newInstance(listener: BCVSelectionListener): ChapterSelectionFragment {
            return ChapterSelectionFragment(listener)
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

        val recyclerView = view as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        viewModel.chapters.observe(viewLifecycleOwner, androidx.lifecycle.Observer { chapterCount ->
            val activity = activity
            if (activity == null || activity.isFinishing) {
                return@Observer
            }

            recyclerView.adapter = NumberSelectionAdapter(chapterCount.chapterCount,
                    CurrentSelected.chapter, this)
        })
    }

    override fun onResume() {
        super.onResume()

        FirebaseAnalytics.getInstance(requireContext()).logEvent("ChapterSelectionFragment", null)

        if (isVisible && CurrentSelected.book != 0) {
            viewModel.loadChapters(CurrentSelected.book.toString())
        }
    }

    override fun numberSelected(number: Int) {
        listener.onChapterSelected(number)
    }
}
