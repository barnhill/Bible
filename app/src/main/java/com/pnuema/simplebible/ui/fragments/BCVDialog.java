package com.pnuema.simplebible.ui.fragments;

import android.content.Intent;
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
import com.pnuema.simplebible.ui.utils.DialogUtils;

public class BCVDialog extends DialogFragment {
    public static final String ARG_STARTING_TAB = "STARTING_POINT";
    private FragmentTabHost mTabHost;
    private ViewPager viewPager;
    private BCVPagerAdapter adapter;
    private int startingTab;

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

    public static BCVDialog instantiate(BCV startingTab) {
        BCVDialog dialog = new BCVDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(BCVDialog.ARG_STARTING_TAB, startingTab.getValue());
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_bookchapterverse_picker, container);

        mTabHost = view.findViewById(R.id.tabs);

        mTabHost.setup(getActivity(), getChildFragmentManager());
        mTabHost.addTab(mTabHost.newTabSpec("tabBook").setIndicator(getString(R.string.book)), Fragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tabChapter").setIndicator(getString(R.string.chapter)), Fragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tabVerse").setIndicator(getString(R.string.verse)), Fragment.class, null);

        adapter = new BCVPagerAdapter(getChildFragmentManager(), getArguments());
        adapter.setTitles(new String[]{getString(R.string.book),getString(R.string.chapter),getString(R.string.verse)});

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
            return BookSelectionFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 3;
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