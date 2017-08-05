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
import android.widget.TextView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.data.Chapters;
import com.pnuema.simplebible.data.Verses;
import com.pnuema.simplebible.retrievers.VersesRetriever;
import com.pnuema.simplebible.statics.CurrentSelected;
import com.pnuema.simplebible.ui.adapters.VersesAdapter;
import com.pnuema.simplebible.ui.utils.DialogUtils;

import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadFragment extends Fragment implements Observer, NotifySelectionCompleted {
    private final VersesRetriever dataRetriever = new VersesRetriever();
    private VersesAdapter mAdapter;

    TextView mBookView;
    TextView mChapterView;
    TextView mVerseView;

    public ReadFragment() {
        // Required empty public constructor
    }

    /**
     * Factory method to create the fragment
     * @return A new instance of fragment ReadFragment.
     */
    public static ReadFragment newInstance() {
        return new ReadFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        setAppBarDisplay();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.versesRecyclerView);
        mAdapter = new VersesAdapter();
        recyclerView.setAdapter(mAdapter);

        mBookView = getActivity().findViewById(R.id.selected_book);
        mChapterView = getActivity().findViewById(R.id.selected_chapter);
        mVerseView = getActivity().findViewById(R.id.selected_verse);

        setAppBarDisplay();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataRetriever.addObserver(this);

        if (CurrentSelected.getChapter() != null && CurrentSelected.getChapter().id != null) {
            dataRetriever.loadData(getContext(), CurrentSelected.getChapter().id);
        }
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
                actionBar.setTitle(CurrentSelected.getVersion().name); //TODO read translation and put here
            }
        }

        //noinspection unchecked
        mAdapter.updateVerses(((Verses) o).response.verses);
        setAppBarDisplay();
    }

    @Override
    public void onSelectionComplete(Books.Book book, Chapters.Chapter chapter, Verses.Verse verse) {
        if (CurrentSelected.getChapter() != null && CurrentSelected.getChapter().id != null) {
            dataRetriever.loadData(getContext(), CurrentSelected.getChapter().id);
        }
    }

    private void setAppBarDisplay() {
        if (mBookView != null) {
            if (CurrentSelected.getBook() != null) {
                mBookView.setText(CurrentSelected.getBook().name);
            }

            mBookView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtils.showBookChapterVersePicker(getActivity(), BCVDialog.BCV.BOOK, ReadFragment.this);
                }
            });
        }

        if (mChapterView != null) {
            mChapterView.setText(CurrentSelected.getChapter() == null ? "1" : CurrentSelected.getChapter().chapter);
            mChapterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtils.showBookChapterVersePicker(getActivity(), BCVDialog.BCV.CHAPTER, ReadFragment.this);
                }
            });
        }

        if (mVerseView != null) {
            mVerseView.setText(CurrentSelected.getVerse() == null ? "1" : CurrentSelected.getVerse().label);
            mVerseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtils.showBookChapterVersePicker(getActivity(), BCVDialog.BCV.VERSE, ReadFragment.this);
                }
            });
        }
    }
}
