package com.pnuema.simplebible.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Verses;
import com.pnuema.simplebible.retrievers.VersesRetriever;
import com.pnuema.simplebible.ui.adapters.VersesAdapter;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadFragment extends Fragment implements Observer {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_VERSION = "VERSION";
    private static final String ARG_BOOK = "BOOK";
    private static final String ARG_CHAPTER = "CHAPTER";
    private static final String ARG_VERSE = "VERSE";

    // TODO: Rename and change types of parameters
    private String mVersion;
    private String mBook;
    private String mChapter;
    private String mVerse;

    private TextView mBookView;
    private TextView mChapterView;
    private TextView mVerseView;

    private VersesAdapter mAdapter;
    private VersesRetriever dataRetriever;

    public ReadFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create the fragment
     *
     * @param version Version of the bible
     * @param book Book of the bible
     * @param chapter Chapter to display
     * @param verse Verse to display
     * @return A new instance of fragment ReadFragment.
     */
    public static ReadFragment newInstance(String version, String book, String chapter, String verse) {
        ReadFragment fragment = new ReadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VERSION, version);
        args.putString(ARG_BOOK, book);
        args.putString(ARG_CHAPTER, chapter);
        args.putString(ARG_VERSE, verse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVersion = getArguments().getString(ARG_VERSION);
            mBook = getArguments().getString(ARG_BOOK);
            mChapter = getArguments().getString(ARG_CHAPTER, "1");
            mVerse = getArguments().getString(ARG_VERSE, "1");
        }

        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        mBookView = activity.findViewById(R.id.selected_book);
        if (mBookView != null) {
            mBookView.setText(mBook);
        }

        mChapterView = activity.findViewById(R.id.selected_chapter);
        if (mChapterView != null) {
            mChapterView.setText(mChapter);
        }

        mVerseView = activity.findViewById(R.id.selected_verse);
        if (mVerseView != null) {
            mVerseView.setText(mVerse);
        }

        dataRetriever = new VersesRetriever();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.versesRecyclerView);
        mAdapter = new VersesAdapter();
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataRetriever.addObserver(this);
        dataRetriever.loadData(getContext(), mBook, mChapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        dataRetriever.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        Activity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(mVersion); //TODO read translation and put here
            }
        }
        //TODO handle data being updated, use DiffUtil

        //noinspection unchecked
        mAdapter.updateVerses((List<Verses.Verse>) o);
    }
}
