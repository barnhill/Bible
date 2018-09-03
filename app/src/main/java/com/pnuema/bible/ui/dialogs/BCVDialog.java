package com.pnuema.bible.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnuema.bible.R;
import com.pnuema.bible.statics.CurrentSelected;
import com.pnuema.bible.ui.fragments.BookSelectionFragment;
import com.pnuema.bible.ui.fragments.ChapterSelectionFragment;
import com.pnuema.bible.ui.fragments.VerseSelectionFragment;

import java.util.ArrayList;

/**
 * Book/Chapter/Verse selection dialog
 */
public class BCVDialog extends DialogFragment implements BCVSelectionListener {
    public static final String ARG_STARTING_TAB = "STARTING_POINT";
    private FragmentTabHost mTabHost;
    private ViewPager viewPager;
    private NotifySelectionCompleted listener;
    private BCVSelectionListener selectionListener;

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
        mTabHost.getTabWidget().setCurrentTab(tab.getValue());
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

        mTabHost = view.findViewById(R.id.tabs);

        mTabHost.setup(getActivity(), getChildFragmentManager());

        final Bundle args = getArguments();

        if (args == null) {
            //TODO log message about arguments being null
            return view;
        }

        final int startTab = args.getInt(BCVDialog.ARG_STARTING_TAB, BCV.BOOK.getValue());
        final ArrayList<String> titles = new ArrayList<>();
        if (startTab == BCV.BOOK.getValue()) {
            mTabHost.addTab(mTabHost.newTabSpec("tabBook").setIndicator(getString(R.string.book)), Fragment.class, null);
            titles.add(getString(R.string.book));
        }

        if (startTab <= BCV.CHAPTER.getValue()) {
            mTabHost.addTab(mTabHost.newTabSpec("tabChapter").setIndicator(getString(R.string.chapter)), Fragment.class, null);
            titles.add(getString(R.string.chapter));
        }

        mTabHost.addTab(mTabHost.newTabSpec("tabVerse").setIndicator(getString(R.string.verse)), Fragment.class, null);
        titles.add(getString(R.string.verse));

        final BCVPagerAdapter adapter = new BCVPagerAdapter(getChildFragmentManager(), getArguments());
        final String[] arrTitles = new String[titles.size()];
        titles.toArray(arrTitles);
        adapter.setTitles(arrTitles);

        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int i, final float v, final int i2) {
            }

            @Override
            public void onPageSelected(final int i) {
                mTabHost.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(final int i) {

            }
        });

        mTabHost.setOnTabChangedListener(s -> {
            final int i = mTabHost.getCurrentTab();
            viewPager.setCurrentItem(i);
        });

        selectionListener = this;

        return view;
    }

    private class BCVPagerAdapter extends FragmentPagerAdapter {
        Bundle bundle;
        String[] titles;

        BCVPagerAdapter(final FragmentManager fm, final Bundle bundle) {
            super(fm);
            this.bundle = bundle;
        }

        @Override
        public Fragment getItem(final int num) {
            if (num == BCV.BOOK.getValue()) {
                return BookSelectionFragment.newInstance(selectionListener);
            } else if (num == BCV.CHAPTER.getValue()) {
                return ChapterSelectionFragment.newInstance(selectionListener);
            } else if (num == BCV.VERSE.getValue()) {
                return VerseSelectionFragment.newInstance(selectionListener);
            } else {
                throw new IllegalStateException("Illegal fragment attempting to be displayed in the BCVDialog");
            }
        }

        @Override
        public int getCount() {
            return mTabHost.getTabWidget().getTabCount();
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            return titles[position];
        }

        void setTitles(final String[] titles) {
            this.titles = titles;
        }
    }
}