package com.pnuema.bible.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnuema.bible.R;
import com.pnuema.bible.data.IChapter;
import com.pnuema.bible.data.IChapterProvider;
import com.pnuema.bible.retrievers.BaseRetriever;
import com.pnuema.bible.retrievers.DBTRetriever;
import com.pnuema.bible.statics.CurrentSelected;
import com.pnuema.bible.ui.adapters.NumberSelectionAdapter;
import com.pnuema.bible.ui.dialogs.BCVSelectionListener;
import com.pnuema.bible.ui.dialogs.NumberSelectionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A fragment representing a list of chapter numbers to pick from.
 * <p/>
 */
public class ChapterSelectionFragment extends Fragment implements Observer, NumberSelectionListener {
    private BCVSelectionListener mListener;
    private final List<IChapter> mChapters = new ArrayList<>();
    private BaseRetriever mRetriever = new DBTRetriever(); //TODO have this select which retriever based on version
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChapterSelectionFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && CurrentSelected.getBook() != null) {
            mRetriever.getChapters(CurrentSelected.getBook().getId());
        }
    }

    @SuppressWarnings("unused")
    public static ChapterSelectionFragment newInstance(BCVSelectionListener listener) {
        return new ChapterSelectionFragment().setListener(listener);
    }

    private ChapterSelectionFragment setListener(BCVSelectionListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_number_list, container, false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        return mRecyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRetriever.addObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRetriever.deleteObservers();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void update(Observable observable, Object o) {
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }

        mChapters.clear();
        if (o instanceof IChapterProvider) {
            //noinspection unchecked
            mChapters.addAll(((IChapterProvider)o).getChapters());
            mRecyclerView.setAdapter(new NumberSelectionAdapter(mChapters.size(), CurrentSelected.getChapter() == null || CurrentSelected.getChapter().getId() == null ? null : Integer.parseInt(CurrentSelected.getChapter().getId()), this));
        }
    }

    @Override
    public void numberSelected(int number) {
        mListener.onChapterSelected(mChapters.get(number));
    }
}
