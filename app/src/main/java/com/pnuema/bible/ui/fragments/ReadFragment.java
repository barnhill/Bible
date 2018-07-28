package com.pnuema.bible.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.bible.R;
import com.pnuema.bible.data.IBook;
import com.pnuema.bible.data.IBookProvider;
import com.pnuema.bible.data.firefly.Verses;
import com.pnuema.bible.data.firefly.Version;
import com.pnuema.bible.data.firefly.Versions;
import com.pnuema.bible.statics.CurrentSelected;
import com.pnuema.bible.ui.adapters.VersesAdapter;
import com.pnuema.bible.ui.dialogs.BCVDialog;
import com.pnuema.bible.ui.dialogs.NotifySelectionCompleted;
import com.pnuema.bible.ui.dialogs.NotifyVersionSelectionCompleted;
import com.pnuema.bible.ui.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * The reading pane fragment
 */
public class ReadFragment extends Fragment implements Observer, NotifySelectionCompleted {
    private VersesAdapter mAdapter;
    private TextView mBookChapterView;
    private TextView mTranslationView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.SmoothScroller mSmoothScroller;
    private List<IBook> books = new ArrayList<>();

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
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_read, container, false);
        final RecyclerView mRecyclerView = view.findViewById(R.id.versesRecyclerView);
        mAdapter = new VersesAdapter();
        mLayoutManager = mRecyclerView.getLayoutManager();
        mRecyclerView.setAdapter(mAdapter);

        final Activity activity = getActivity();
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
        CurrentSelected.getRetriever().addObserver(this);

        if (CurrentSelected.getChapter() != null && CurrentSelected.getChapter() != null) {
            CurrentSelected.getRetriever().getVerses(CurrentSelected.getVersion(), String.valueOf(CurrentSelected.getBook()), String.valueOf(CurrentSelected.getChapter()));
        }

        if (CurrentSelected.getVersion() != null) {
            CurrentSelected.getRetriever().getVersions();
        }

        if (CurrentSelected.getBook() != null && books.isEmpty()) {
            CurrentSelected.getRetriever().getBooks();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        CurrentSelected.getRetriever().deleteObserver(this);
    }

    @Override
    public void update(final Observable observable, final Object o) {
        final Activity activity = getActivity();
        if (activity instanceof AppCompatActivity && o instanceof Versions) {
            final List<Version> versions = ((Versions)o).getVersions();
            for (final Version version : versions) {
                if (version.getAbbreviation().equals(CurrentSelected.getVersion())) {
                    mTranslationView.setText(version.getAbbreviation().toUpperCase());
                    break;
                }
            }
        }

        if (o instanceof Verses) {
            //noinspection unchecked
            mAdapter.updateVerses(((Verses) o).getVerses());

            if (CurrentSelected.getVerse() != null) {
                new Handler().post(() -> scrollToVerse(CurrentSelected.getVerse()));
            }

            setBookChapterText();
        } else if (o instanceof IBookProvider) {
            books.clear();
            books.addAll(((IBookProvider)o).getBooks());
            setBookChapterText();
        }
    }

    @Override
    public void onSelectionPreloadChapter(final int book, final int chapter) {
        if (CurrentSelected.getChapter() != null && CurrentSelected.getChapter() != null) {
            CurrentSelected.getRetriever().getVerses(CurrentSelected.getVersion(), String.valueOf(CurrentSelected.getBook()), String.valueOf(CurrentSelected.getChapter()));
        }
    }

    @Override
    public void onSelectionComplete(final int book, final int chapter, final int verse) {
        if (CurrentSelected.getChapter() != null && CurrentSelected.getChapter() != null) {
            CurrentSelected.getRetriever().getVerses(CurrentSelected.getVersion(), String.valueOf(CurrentSelected.getBook()), String.valueOf(CurrentSelected.getChapter()));
            scrollToVerse(verse);
        }
    }

    private void scrollToVerse(@Nullable final Integer verse) {
        if (verse == null) {
            return;
        }
        mSmoothScroller.setTargetPosition(verse - 1);
        mLayoutManager.startSmoothScroll(mSmoothScroller);
    }

    private void setAppBarDisplay() {
        if (!isAdded() || getActivity() == null) {
            return;
        }
        if (mBookChapterView != null) {
            setBookChapterText();
            mBookChapterView.setOnClickListener(view -> DialogUtils.showBookChapterVersePicker(getActivity(), BCVDialog.BCV.BOOK, ReadFragment.this));
        }

        if (mTranslationView != null) {
            mTranslationView.setOnClickListener(view -> DialogUtils.showVersionsPicker(getActivity(), (NotifyVersionSelectionCompleted) getActivity()));
        }
    }

    private void setBookChapterText() {
        if (mBookChapterView != null && CurrentSelected.getBook() != null && CurrentSelected.getChapter() != null) {
            for (final IBook book : books) {
                if (book.getId() == CurrentSelected.getBook()) {
                    mBookChapterView.setText(getString(R.string.book_chapter_header_format, book.getName(), CurrentSelected.getChapter()));
                }
            }
        }
    }
}
