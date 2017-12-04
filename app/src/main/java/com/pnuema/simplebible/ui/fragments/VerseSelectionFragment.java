package com.pnuema.simplebible.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.IVerse;
import com.pnuema.simplebible.data.IVerseProvider;
import com.pnuema.simplebible.retrievers.BaseRetriever;
import com.pnuema.simplebible.retrievers.DBTRetriever;
import com.pnuema.simplebible.statics.CurrentSelected;
import com.pnuema.simplebible.ui.dialogs.BCVSelectionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A fragment representing a list of verse numbers to pick from.
 * <p/>
 */
public class VerseSelectionFragment extends Fragment implements Observer {
    private BCVSelectionListener mListener;
    private final List<IVerse> mVerses = new ArrayList<>();
    private BaseRetriever mRetriever = new DBTRetriever(); //TODO have this select which retriever based on version
    private GridView mGridView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VerseSelectionFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mRetriever.addObserver(this);
        if (isVisibleToUser && CurrentSelected.getChapter() != null) {
            mRetriever.getVerses(getContext(), CurrentSelected.getVersion().getId(), CurrentSelected.getBook().getAbbreviation(), CurrentSelected.getChapter().getName());
        }
    }

    @SuppressWarnings("unused")
    public static VerseSelectionFragment newInstance(BCVSelectionListener listener) {
        return new VerseSelectionFragment().setListener(listener);
    }

    private VerseSelectionFragment setListener(BCVSelectionListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mGridView = (GridView) inflater.inflate(R.layout.fragment_number_list, container, false);
        return mGridView;
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
        Context context = getContext();
        if (context == null || getActivity() == null || getActivity().isFinishing()) {
            return;
        }

        mVerses.clear();
        if (o instanceof IVerseProvider && ((IVerseProvider)o).getVerses() != null) {
            mVerses.addAll(((IVerseProvider)o).getVerses());
        }

        List<Integer> mList = new ArrayList<>();
        for (int i = 1; i <= mVerses.size(); i++) {
            mList.add(i);
        }

        mGridView.setAdapter(new ArrayAdapter<>(context, R.layout.item_number, mList));
        mGridView.setOnItemClickListener((adapterView, view, i, l) -> mListener.onVerseSelected(mVerses.get(i)));
    }
}
