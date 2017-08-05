package com.pnuema.simplebible.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.data.Chapters;
import com.pnuema.simplebible.data.Verses;
import com.pnuema.simplebible.statics.CurrentSelected;

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
    public void onBookSelected(Books.Book book) {
        CurrentSelected.setBook(book);
        CurrentSelected.clearChapter();
        CurrentSelected.clearVerse();
        gotoTab(BCV.CHAPTER);
    }

    @Override
    public void onChapterSelected(Chapters.Chapter chapter) {
        CurrentSelected.setChapter(chapter);
        CurrentSelected.clearVerse();
        gotoTab(BCV.VERSE);
    }

    @Override
    public void onVerseSelected(Verses.Verse verse) {
        CurrentSelected.setVerse(verse);
        refresh();
    }

    private void gotoTab(BCV tab) {
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
        BCV(int i) {
            value = i;
        }

        public int getValue() {
            return value;
        }
    }

    public static BCVDialog instantiate(BCV startingTab, NotifySelectionCompleted notifySelectionCompleted) {
        BCVDialog dialog = new BCVDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(BCVDialog.ARG_STARTING_TAB, startingTab.getValue());
        dialog.setArguments(bundle);
        dialog.setListener(notifySelectionCompleted);

        return dialog;
    }

    private BCVDialog setListener(NotifySelectionCompleted listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_bookchapterverse_picker, container);

        mTabHost = view.findViewById(R.id.tabs);

        mTabHost.setup(getActivity(), getChildFragmentManager());

        Bundle args = getArguments();
        int startTab = args.getInt(BCVDialog.ARG_STARTING_TAB, BCV.BOOK.getValue());
        ArrayList<String> titles = new ArrayList<>();
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

        BCVPagerAdapter adapter = new BCVPagerAdapter(getChildFragmentManager(), getArguments());
        String[] arrTitles = new String[titles.size()];
        titles.toArray(arrTitles);
        adapter.setTitles(arrTitles);

        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                mTabHost.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int i = mTabHost.getCurrentTab();
                viewPager.setCurrentItem(i);
            }
        });

        selectionListener = this;

        return view;
    }

    private class BCVPagerAdapter extends FragmentPagerAdapter {
        Bundle bundle;
        String[] titles;

        BCVPagerAdapter(FragmentManager fm, Bundle bundle) {
            super(fm);
            this.bundle = bundle;
        }

        @Override
        public Fragment getItem(int num) {
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
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        void setTitles(String[] titles) {
            this.titles = titles;
        }
    }
}