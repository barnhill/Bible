package com.pnuema.android.bible.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.pnuema.android.bible.R
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.ui.SectionsPagerAdapter

/**
 * Book/Chapter/Verse selection dialog
 */
class BCVDialog : DialogFragment(), BCVSelectionListener {
    private lateinit var viewPager: ViewPager
    private var listener: NotifySelectionCompleted? = null

    companion object {
        private const val ARG_STARTING_TAB = "STARTING_POINT"

        fun instantiate(startingTab: BCV, notifySelectionCompleted: NotifySelectionCompleted): BCVDialog {
            val dialog = BCVDialog()
            val bundle = Bundle()
            bundle.putInt(ARG_STARTING_TAB, startingTab.value)
            dialog.arguments = bundle
            dialog.setListener(notifySelectionCompleted)

            return dialog
        }
    }

    override fun onBookSelected(book: Int) {
        CurrentSelected.setBook(book)
        CurrentSelected.clearChapter()
        CurrentSelected.clearVerse()
        gotoTab(BCV.CHAPTER)
    }

    override fun onChapterSelected(chapter: Int) {
        CurrentSelected.setChapter(chapter)
        CurrentSelected.clearVerse()
        gotoTab(BCV.VERSE)
    }

    override fun onVerseSelected(verse: Int) {
        CurrentSelected.setVerse(verse)
        refresh()
    }

    private fun gotoTab(tab: BCV) {
        if (::viewPager.isInitialized) {
            viewPager.currentItem = tab.value
        }
    }

    override fun refresh() {
        if (listener != null) {
            listener!!.onSelectionComplete(CurrentSelected.getBook()!!, CurrentSelected.getChapter()!!, CurrentSelected.getVerse()!!)
        }
        dismiss()
    }

    enum class BCV private constructor(val value: Int) {
        BOOK(0), CHAPTER(1), VERSE(2)
    }

    private fun setListener(listener: NotifySelectionCompleted) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_bookchapterverse_picker, container)

        val pagerAdapter = SectionsPagerAdapter(childFragmentManager, context!!, this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = pagerAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        val args = arguments
                ?: //TODO log message about arguments being null
                return view

        val startTab = args.getInt(ARG_STARTING_TAB, BCV.BOOK.value)

        viewPager.currentItem = startTab

        return view
    }
}