package com.pnuema.simplebible.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.IBook;
import com.pnuema.simplebible.data.IBookProvider;
import com.pnuema.simplebible.retrievers.BaseRetreiver;
import com.pnuema.simplebible.retrievers.BiblesOrgRetriever;
import com.pnuema.simplebible.ui.adapters.BookSelectionRecyclerViewAdapter;
import com.pnuema.simplebible.ui.dialogs.BCVSelectionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A fragment representing a list of books to pick from.
 * <p/>
 */
public class BookSelectionFragment extends Fragment implements Observer {
    private BCVSelectionListener mListener;
    private final List<IBook> mBooks = new ArrayList<>();
    private BaseRetreiver mRetriever = new BiblesOrgRetriever(); //TODO have this select which retriever based on version
    private BookSelectionRecyclerViewAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookSelectionFragment() {
    }

    @SuppressWarnings("unused")
    public static BookSelectionFragment newInstance(BCVSelectionListener listener) {
        return new BookSelectionFragment().setListener(listener);
    }

    private BookSelectionFragment setListener(BCVSelectionListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            mAdapter = new BookSelectionRecyclerViewAdapter(mBooks, mListener);
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setAdapter(mAdapter);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRetriever.addObserver(this);
        mRetriever.getBooks(getContext());
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
        mBooks.clear();
        if (o instanceof IBookProvider && ((IBookProvider)o).getBooks() != null) {
            mBooks.addAll(((IBookProvider)o).getBooks());
            mAdapter.notifyDataSetChanged();
        }
    }
}
