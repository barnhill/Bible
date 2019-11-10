package com.pnuema.android.bible.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.data.firefly.ChapterCount
import com.pnuema.android.bible.retrievers.FireflyRetriever
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.ui.adapters.NumberSelectionAdapter
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener
import com.pnuema.android.bible.ui.dialogs.NumberSelectionListener
import java.util.*

/**
 * A fragment representing a list of chapter numbers to pick from.
 *
 *
 */
class ChapterSelectionFragment : Fragment(), Observer, NumberSelectionListener {
    private lateinit var mListener: BCVSelectionListener
    private lateinit var mRecyclerView: RecyclerView
    private val mRetriever = FireflyRetriever()

    companion object {
        fun newInstance(listener: BCVSelectionListener): ChapterSelectionFragment {
            return ChapterSelectionFragment().setListener(listener)
        }
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    init {
        setHasOptionsMenu(true)
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)

        if (menuVisible && CurrentSelected.getBook() != null) {
            mRetriever.addObserver(this)
            mRetriever.getChapters(CurrentSelected.getBook().toString())
        }

        if (!menuVisible) {
            mRetriever.deleteObservers()
        }
    }

    private fun setListener(listener: BCVSelectionListener): ChapterSelectionFragment {
        mListener = listener
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRecyclerView = inflater.inflate(R.layout.fragment_number_list, container, false) as RecyclerView
        mRecyclerView.layoutManager = GridLayoutManager(context, 3)
        return mRecyclerView
    }

    override fun update(observable: Observable, o: Any) {
        val activity = activity
        if (activity == null || activity.isFinishing) {
            return
        }

        if (o is ChapterCount) {
            mRecyclerView.adapter = NumberSelectionAdapter(o.chapterCount,
                    CurrentSelected.getChapter(),
                    this)
        }
    }

    override fun numberSelected(number: Int) {
        mListener.onChapterSelected(number)
    }
}
