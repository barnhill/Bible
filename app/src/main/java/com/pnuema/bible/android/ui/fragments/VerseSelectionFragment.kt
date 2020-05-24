package com.pnuema.bible.android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.firefly.VerseCount
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.adapters.NumberSelectionAdapter
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.dialogs.NumberSelectionListener
import com.pnuema.bible.android.ui.fragments.viewModel.VersesViewModel

/**
 * A fragment representing a list of verse numbers to pick from.
 *
 *
 */
class VerseSelectionFragment(private val listener: BCVSelectionListener) : Fragment(R.layout.fragment_number_list), NumberSelectionListener {
    private lateinit var viewModel: VersesViewModel

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

        val gridView = view as RecyclerView
        gridView.layoutManager = GridLayoutManager(context, 3)

        viewModel = ViewModelProvider(this).get(VersesViewModel::class.java)
        viewModel.verses.observe(viewLifecycleOwner, Observer { verseCount ->
            if (activity == null || activity?.isFinishing != false) {
                return@Observer
            }

            if (verseCount is VerseCount) {
                gridView.adapter = NumberSelectionAdapter(verseCount.verseCount, CurrentSelected.verse, this)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        if (isVisible && CurrentSelected.chapter != CurrentSelected.DEFAULT_VALUE) {
            viewModel.loadVerses(CurrentSelected.version, CurrentSelected.book.toString(), CurrentSelected.chapter.toString())
        }
    }

    override fun numberSelected(number: Int) {
        listener.onVerseSelected(number)
    }
}
