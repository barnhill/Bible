package com.pnuema.simplebible.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.IBook;
import com.pnuema.simplebible.data.IChapter;
import com.pnuema.simplebible.data.IVerse;
import com.pnuema.simplebible.data.IVerseProvider;
import com.pnuema.simplebible.retrievers.BaseRetriever;
import com.pnuema.simplebible.retrievers.BiblesOrgRetriever;
import com.pnuema.simplebible.statics.CurrentSelected;
import com.pnuema.simplebible.ui.adapters.VersesAdapter;
import com.pnuema.simplebible.ui.dialogs.BCVDialog;
import com.pnuema.simplebible.ui.dialogs.NotifySelectionCompleted;
import com.pnuema.simplebible.ui.dialogs.NotifyVersionSelectionCompleted;
import com.pnuema.simplebible.ui.utils.DialogUtils;

import java.util.Observable;
import java.util.Observer;

/**
 * The reading pane fragment
 */
public class ReadFragment extends Fragment implements Observer, NotifySelectionCompleted {
    private final BaseRetriever dataRetriever = new BiblesOrgRetriever(); //TODO have this select which retriever based on version
    private VersesAdapter mAdapter;
    private TextView mBookChapterView;
    private TextView mTranslationView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.SmoothScroller mSmoothScroller;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        RecyclerView mRecyclerView = view.findViewById(R.id.versesRecyclerView);
        mAdapter = new VersesAdapter();
        mLayoutManager = mRecyclerView.getLayoutManager();
        mRecyclerView.setAdapter(mAdapter);

        Activity activity = getActivity();
        if (activity == null) {
            return view;
        }

        mSmoothScroller = new LinearSmoothScroller(activity) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        mBookChapterView = activity.findViewById(R.id.selected_book);
        mTranslationView = activity.findViewById(R.id.selected_translation);

        setAppBarDisplay();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataRetriever.addObserver(this);

        if (CurrentSelected.getChapter() != null && CurrentSelected.getChapter().getId() != null) {
            dataRetriever.getVerses(getContext(), CurrentSelected.getVersion().getId(), CurrentSelected.getBook().getAbbreviation(), CurrentSelected.getChapter().getName());
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
                actionBar.setTitle(CurrentSelected.getVersion().getDisplayText()); //TODO read translation and put here
            }
        }

        if (o instanceof IVerseProvider) {
            //noinspection unchecked
            mAdapter.updateVerses(((IVerseProvider) o).getVerses());

            if (!TextUtils.isEmpty(CurrentSelected.getVerse().getVerseNumber()) && TextUtils.isDigitsOnly(CurrentSelected.getVerse().getVerseNumber())) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        scrollToVerse(CurrentSelected.getVerse());
                    }
                });
            }
        }

        setAppBarDisplay();
    }

    @Override
    public void onSelectionPreloadChapter(IBook book, IChapter chapter) {
        if (CurrentSelected.getChapter() != null && CurrentSelected.getChapter() != null) {
            dataRetriever.getVerses(getContext(), CurrentSelected.getVersion().getId(), CurrentSelected.getBook().getAbbreviation(), CurrentSelected.getChapter().getName());
        }
    }

    @Override
    public void onSelectionComplete(IBook book, IChapter chapter, IVerse verse) {
        if (CurrentSelected.getChapter() != null && CurrentSelected.getChapter().getId() != null) {
            dataRetriever.getVerses(getContext(), CurrentSelected.getVersion().getId(), CurrentSelected.getBook().getAbbreviation(), CurrentSelected.getChapter().getName());
            scrollToVerse(CurrentSelected.getVerse());
        }
    }

    private void scrollToVerse(IVerse verse) {
        mSmoothScroller.setTargetPosition(Integer.parseInt(verse.getVerseNumber()) - 1);
        mLayoutManager.startSmoothScroll(mSmoothScroller);
    }

    private void setAppBarDisplay() {
        if (mBookChapterView != null) {
            if (CurrentSelected.getBook() != null) {
                mBookChapterView.setText(getString(R.string.book_chapter_header_format, CurrentSelected.getBook().getName(), CurrentSelected.getChapter().getName()));
            }

            mBookChapterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtils.showBookChapterVersePicker(getActivity(), BCVDialog.BCV.BOOK, ReadFragment.this);
                }
            });
        }

        if (mTranslationView != null) {
            mTranslationView.setText(CurrentSelected.getChapter() == null ? "<?>" : CurrentSelected.getVersion().getAbbreviation());
            mTranslationView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogUtils.showVersionsPicker(getActivity(), (NotifyVersionSelectionCompleted) getActivity());
                }
            });
        }
    }
}
