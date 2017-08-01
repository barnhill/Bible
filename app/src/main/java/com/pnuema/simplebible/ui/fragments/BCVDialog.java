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

public class BCVDialog extends DialogFragment {
    private FragmentTabHost mTabHost;
    private ViewPager viewPager;
    private BCVPagerAdapter adapter;

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
        String [] titles;

        private BCVPagerAdapter(FragmentManager fm) {
            super(fm);
        }

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

    /*public static class BCVListFragment extends ListFragment {
        List<String> voters;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.list_fragment, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            voters = (ArrayList) getArguments().getSerializable("voters");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, voters);
            setListAdapter(adapter);

            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), ProfileActivity_.class);
                    String login = voters.get(i);
                    intent.putExtra("login", Utils.encodeString(login.substring(0, login.indexOf("(")).trim()));
                    startActivity(intent);
                }
            });
        }
    }*/

}