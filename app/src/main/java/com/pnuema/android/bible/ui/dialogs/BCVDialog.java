package com.pnuema.android.bible.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.pnuema.android.bible.R;
import com.pnuema.android.bible.statics.CurrentSelected;
import com.pnuema.android.bible.ui.SectionsPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

/**
 * Book/Chapter/Verse selection dialog
 */
public class BCVDialog extends DialogFragment implements BCVSelectionListener {
    private static final String ARG_STARTING_TAB = "STARTING_POINT";
    private SectionsPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private NotifySelectionCompleted listener;

    @Override
    public void onBookSelected(final int book) {
        CurrentSelected.setBook(book);
        CurrentSelected.clearChapter();
        CurrentSelected.clearVerse();
        gotoTab(BCV.CHAPTER);
    }

    @Override
    public void onChapterSelected(final int chapter) {
        CurrentSelected.setChapter(chapter);
        CurrentSelected.clearVerse();
        gotoTab(BCV.VERSE);
    }

    @Override
    public void onVerseSelected(final int verse) {
        CurrentSelected.setVerse(verse);
        refresh();
    }

    private void gotoTab(final BCV tab) {
        viewPager.setCurrentItem(tab.getValue());
    }

    @Override
    public void refresh() {
        if (listener != null) {
            listener.onSelectionComplete(CurrentSelected.getBook(), CurrentSelected.getChapter(), CurrentSelected.getVerse());
        }
        dismiss();
    }

    public enum BCV {
        BOOK(0), CHAPTER(1), VERSE(2);

        private int value;
        BCV(final int i) {
            value = i;
        }

        public int getValue() {
            return value;
        }
    }

    public static BCVDialog instantiate(final BCV startingTab, final NotifySelectionCompleted notifySelectionCompleted) {
        final BCVDialog dialog = new BCVDialog();
        final Bundle bundle = new Bundle();
        bundle.putInt(BCVDialog.ARG_STARTING_TAB, startingTab.getValue());
        dialog.setArguments(bundle);
        dialog.setListener(notifySelectionCompleted);

        return dialog;
    }

    private void setListener(final NotifySelectionCompleted listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_bookchapterverse_picker, container);

        if (getActivity() == null) {
            return null;
        }

        pagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), getContext(), this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        final Bundle args = getArguments();

        if (args == null) {
            //TODO log message about arguments being null
            return view;
        }

        final int startTab = args.getInt(BCVDialog.ARG_STARTING_TAB, BCV.BOOK.getValue());

        viewPager.setCurrentItem(startTab);

        return view;
    }
}