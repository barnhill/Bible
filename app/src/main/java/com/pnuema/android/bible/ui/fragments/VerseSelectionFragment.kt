package com.pnuema.android.bible.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.data.firefly.VerseCount
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.ui.adapters.NumberSelectionAdapter
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener
import com.pnuema.android.bible.ui.dialogs.NumberSelectionListener
import com.pnuema.android.bible.ui.fragments.viewModel.VersesViewModel

/**
 * A fragment representing a list of verse numbers to pick from.
 *
 *
 */
class VerseSelectionFragment(private val listener: BCVSelectionListener) : Fragment(), NumberSelectionListener {
    private lateinit var mGridView: RecyclerView
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mGridView = inflater.inflate(R.layout.fragment_number_list, container, false) as RecyclerView
        mGridView.layoutManager = GridLayoutManager(context, 3)

        viewModel = ViewModelProviders.of(this).get(VersesViewModel::class.java)
        viewModel.verses.observe(viewLifecycleOwner, Observer { verseCount ->
            val context = context
            if (context == null || activity == null || activity!!.isFinishing) {
                return@Observer
            }

            if (verseCount is VerseCount) {
                mGridView.adapter = NumberSelectionAdapter(verseCount.verseCount, CurrentSelected.getVerse(), this)
            }
        })

        return mGridView
    }

    override fun onResume() {
        super.onResume()

        if (isMenuVisible && CurrentSelected.getChapter() != null) {
            viewModel.loadVerses(CurrentSelected.getVersion(), CurrentSelected.getBook().toString(), CurrentSelected.getChapter().toString())
        }
    }

    override fun numberSelected(number: Int) {
        listener.onVerseSelected(number)
    }
}
