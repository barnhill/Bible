package com.pnuema.simplebible.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.data.Chapters;
import com.pnuema.simplebible.retrievers.BooksRetriever;
import com.pnuema.simplebible.retrievers.ChaptersRetriever;
import com.pnuema.simplebible.ui.adapters.BookSelectionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class ChapterSelectionFragment extends Fragment implements Observer {
    private BCVSelectionListener mListener;
    private final List<Chapters.Chapter> mChapters = new ArrayList<>();
    private ChaptersRetriever mRetriever = new ChaptersRetriever();
    private GridView mGridView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChapterSelectionFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && BCVDialog.mBook != null) {
            mRetriever.loadData(getContext(), BCVDialog.mBook.id);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mGridView = (GridView) inflater.inflate(R.layout.fragment_number_list, container, false);
        return mGridView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRetriever.addObserver(this);
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
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }

        mChapters.clear();
        if (o instanceof Chapters && ((Chapters)o).response != null && ((Chapters)o).response.chapters != null) {
            mChapters.addAll(((Chapters)o).response.chapters);
        }

        List<Integer> mList = new ArrayList<>();
        for (int i = 1; i < mChapters.size(); i++) {
            mList.add(i);
        }

        mGridView.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_number, mList));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int chapterId = Integer.parseInt(((TextView)view).getText().toString());
                mListener.onChapterSelected(mChapters.get(chapterId));
            }
        });
    }
}
