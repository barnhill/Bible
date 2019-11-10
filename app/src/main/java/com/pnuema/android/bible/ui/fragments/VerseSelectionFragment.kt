package com.pnuema.android.bible.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.data.firefly.VerseCount
import com.pnuema.android.bible.retrievers.FireflyRetriever
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.ui.adapters.NumberSelectionAdapter
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener
import com.pnuema.android.bible.ui.dialogs.NumberSelectionListener
import java.util.*

/**
 * A fragment representing a list of verse numbers to pick from.
 *
 *
 */
class VerseSelectionFragment : Fragment(), Observer, NumberSelectionListener {
    private lateinit var mListener: BCVSelectionListener
    private lateinit var mGridView: RecyclerView
    private val mRetriever = FireflyRetriever()

    companion object {
        fun newInstance(listener: BCVSelectionListener): VerseSelectionFragment {
            return VerseSelectionFragment().setListener(listener)
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

        if (menuVisible && CurrentSelected.getChapter() != null) {
            mRetriever.addObserver(this)
            mRetriever.getVerseCount(CurrentSelected.getVersion(), CurrentSelected.getBook().toString(), CurrentSelected.getChapter().toString())
        }

        if (!menuVisible) {
            mRetriever.deleteObservers()
        }
    }

    private fun setListener(listener: BCVSelectionListener): VerseSelectionFragment {
        mListener = listener
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mGridView = inflater.inflate(R.layout.fragment_number_list, container, false) as RecyclerView
        mGridView.layoutManager = GridLayoutManager(context, 3)
        return mGridView
    }

    override fun update(observable: Observable, o: Any) {
        val context = context
        if (context == null || activity == null || activity!!.isFinishing) {
            return
        }

        if (o is VerseCount) {
            mGridView.adapter = NumberSelectionAdapter(o.verseCount, CurrentSelected.getVerse(), this)
        }
    }

    override fun numberSelected(number: Int) {
        mListener.onVerseSelected(number)
    }
}
