package com.pnuema.android.bible.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.ui.adapters.NumberSelectionAdapter
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener
import com.pnuema.android.bible.ui.dialogs.NumberSelectionListener
import com.pnuema.android.bible.ui.fragments.viewModel.ChaptersViewModel

/**
 * A fragment representing a list of chapter numbers to pick from.
 */
class ChapterSelectionFragment(private val listener: BCVSelectionListener) : Fragment(), NumberSelectionListener {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var viewModel: ChaptersViewModel

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRecyclerView = inflater.inflate(R.layout.fragment_number_list, container, false) as RecyclerView
        mRecyclerView.layoutManager = GridLayoutManager(context, 3)

        viewModel = ViewModelProviders.of(this).get(ChaptersViewModel::class.java)
        viewModel.chapters.observe(viewLifecycleOwner, androidx.lifecycle.Observer { chapterCount ->
            val activity = activity
            if (activity == null || activity.isFinishing) {
                return@Observer
            }

            mRecyclerView.adapter = NumberSelectionAdapter(chapterCount.chapterCount,
                    CurrentSelected.getChapter(), this)
        })

        return mRecyclerView
    }

    override fun onResume() {
        super.onResume()

        if (isMenuVisible && CurrentSelected.getBook() != null) {
            viewModel.loadChapters(CurrentSelected.getBook().toString())
        }
    }

    override fun numberSelected(number: Int) {
        listener.onChapterSelected(number)
    }
}
