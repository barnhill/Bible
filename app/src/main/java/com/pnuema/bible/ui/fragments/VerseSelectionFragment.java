package com.pnuema.bible.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnuema.bible.R;
import com.pnuema.bible.data.firefly.VerseCount;
import com.pnuema.bible.retrievers.BaseRetriever;
import com.pnuema.bible.retrievers.FireflyRetriever;
import com.pnuema.bible.statics.CurrentSelected;
import com.pnuema.bible.ui.adapters.NumberSelectionAdapter;
import com.pnuema.bible.ui.dialogs.BCVSelectionListener;
import com.pnuema.bible.ui.dialogs.NumberSelectionListener;

import java.util.Observable;
import java.util.Observer;

/**
 * A fragment representing a list of verse numbers to pick from.
 * <p/>
 */
public class VerseSelectionFragment extends Fragment implements Observer, NumberSelectionListener {
    private BCVSelectionListener mListener;
    private BaseRetriever mRetriever = new FireflyRetriever();
    private RecyclerView mGridView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VerseSelectionFragment() {
    }

    @Override
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mRetriever.addObserver(this);
        if (isVisibleToUser && CurrentSelected.getChapter() != null) {
            mRetriever.getVerseCount(CurrentSelected.getVersion(), String.valueOf(CurrentSelected.getBook()), String.valueOf(CurrentSelected.getChapter()));
        }
    }

    @SuppressWarnings("unused")
    public static VerseSelectionFragment newInstance(final BCVSelectionListener listener) {
        return new VerseSelectionFragment().setListener(listener);
    }

    private VerseSelectionFragment setListener(final BCVSelectionListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        mGridView = (RecyclerView) inflater.inflate(R.layout.fragment_number_list, container, false);
        mGridView.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
    public void update(final Observable observable, final Object o) {
        final Context context = getContext();
        if (context == null || getActivity() == null || getActivity().isFinishing()) {
            return;
        }

        if (o instanceof VerseCount) {
            mGridView.setAdapter(new NumberSelectionAdapter(((VerseCount)o).getVerseCount(), CurrentSelected.getVerse(), this));
        }
    }

    @Override
    public void numberSelected(final int number) {
        mListener.onVerseSelected(number);
    }
}
