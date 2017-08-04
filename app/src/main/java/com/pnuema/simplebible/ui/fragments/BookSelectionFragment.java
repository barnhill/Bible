package com.pnuema.simplebible.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.retrievers.BooksRetriever;
import com.pnuema.simplebible.ui.adapters.BookSelectionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class BookSelectionFragment extends Fragment implements Observer {
    private BCVSelectionListener mListener;
    private final List<Books.Book> mBooks = new ArrayList<>();
    private BooksRetriever mRetriever = new BooksRetriever();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mAdapter = new BookSelectionRecyclerViewAdapter(mBooks, mListener);
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(mAdapter);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRetriever.addObserver(this);
        mRetriever.loadData(getContext(), "eng-KJVA"); //TODO read in version
    }

    @Override
    public void onPause() {
        super.onPause();
        mRetriever.deleteObserver(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnBookSelectionFragmentInteractionListener) {
            mListener = (OnBookSelectionFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void update(Observable observable, Object o) {
        mBooks.clear();
        if (o instanceof Books && ((Books)o).response != null && ((Books)o).response.books != null) {
            mBooks.addAll(((Books)o).response.books);
            mAdapter.notifyDataSetChanged();
        }
    }
}
