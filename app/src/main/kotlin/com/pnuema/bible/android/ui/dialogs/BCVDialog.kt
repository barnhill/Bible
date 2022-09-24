package com.pnuema.bible.android.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.DialogBookchapterversePickerBinding
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.CurrentSelected.chapter
import com.pnuema.bible.android.ui.SectionsPagerAdapter

/**
 * Book/Chapter/Verse selection dialog
 */
class BCVDialog : Fragment(R.layout.dialog_bookchapterverse_picker), BCVSelectionListener {
    private var _binding: DialogBookchapterversePickerBinding? = null
    private val binding: DialogBookchapterversePickerBinding get() = _binding!!
    private var listener: NotifySelectionCompleted? = null

    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().supportFragmentManager.popBackStack()
            remove()
        }
    }

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

    enum class BCV constructor(val value: Int) {
        BOOK(0), CHAPTER(1), VERSE(2)
    }

    override fun onBookSelected(book: Int) {
        if (CurrentSelected.book != book) {
            CurrentSelected.book = book
            CurrentSelected.clearChapter()
            CurrentSelected.clearVerse()
        }
        gotoTab(BCV.CHAPTER)
    }

    override fun onChapterSelected(chapter: Int) {
        if (CurrentSelected.chapter != chapter) {
            CurrentSelected.chapter = chapter
            CurrentSelected.clearVerse()
        }
        gotoTab(BCV.VERSE)
    }

    override fun onVerseSelected(verse: Int) {
        CurrentSelected.verse = verse
        refresh()
    }

    private fun gotoTab(tab: BCV) {
        binding.pager.currentItem = tab.value
    }

    override fun refresh() {
        listener?.onSelectionComplete(CurrentSelected.book, chapter, CurrentSelected.verse)
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setListener(listener: NotifySelectionCompleted) {
        this.listener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = DialogBookchapterversePickerBinding.bind(view)

        val pagerAdapter = SectionsPagerAdapter(this, view.context, this)
        binding.pager.adapter = pagerAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        TabLayoutMediator(tabLayout, binding.pager) { tab, position ->
            tab.text = pagerAdapter.getPageTitle(position)
        }.attach()

        val args = arguments ?: return //TODO log message about arguments being null

        val startTab = args.getInt(ARG_STARTING_TAB, BCV.BOOK.value)

        binding.pager.currentItem = startTab

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}