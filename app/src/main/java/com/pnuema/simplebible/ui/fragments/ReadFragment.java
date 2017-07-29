package com.pnuema.simplebible.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Verses;
import com.pnuema.simplebible.retrievers.VersesRetriever;
import com.pnuema.simplebible.retrofit.API;
import com.pnuema.simplebible.retrofit.IAPI;
import com.pnuema.simplebible.ui.adapters.VersesAdapter;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    // TODO: Rename and change types and number of parameters
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
            mChapter = getArguments().getString(ARG_CHAPTER);
            mVerse = getArguments().getString(ARG_VERSE);
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
        dataRetriever.loadData(getContext(), mVersion, mBook, mChapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        dataRetriever.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        //TODO handle data being updated, use DiffUtil

        //noinspection unchecked
        mAdapter.updateVerses((List<Verses.Verse>) o);
    }
}
