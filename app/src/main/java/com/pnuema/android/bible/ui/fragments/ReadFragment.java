package com.pnuema.android.bible.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.android.bible.R;
import com.pnuema.android.bible.data.IBook;
import com.pnuema.android.bible.data.IBookProvider;
import com.pnuema.android.bible.data.IVerseProvider;
import com.pnuema.android.bible.data.IVersion;
import com.pnuema.android.bible.data.IVersionProvider;
import com.pnuema.android.bible.retrievers.BaseRetriever;
import com.pnuema.android.bible.retrievers.FireflyRetriever;
import com.pnuema.android.bible.statics.CurrentSelected;
import com.pnuema.android.bible.ui.adapters.VersesAdapter;
import com.pnuema.android.bible.ui.dialogs.BCVDialog;
import com.pnuema.android.bible.ui.dialogs.NotifySelectionCompleted;
import com.pnuema.android.bible.ui.dialogs.NotifyVersionSelectionCompleted;
import com.pnuema.android.bible.ui.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

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
    private BaseRetriever mRetriever = new FireflyRetriever();

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
        mRetriever.addObserver(this);

        refresh();
    }

    public void refresh() {
        if (CurrentSelected.getChapter() != null) {
            mRetriever.getVerses(CurrentSelected.getVersion(), String.valueOf(CurrentSelected.getBook()), String.valueOf(CurrentSelected.getChapter()));
        }

        if (CurrentSelected.getVersion() != null) {
            mRetriever.getVersions();
        }

        if (CurrentSelected.getBook() != null && books.isEmpty()) {
            mRetriever.getBooks();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mRetriever.deleteObserver(this);
    }

    @Override
    public void update(final Observable observable, final Object o) {
        final Activity activity = getActivity();
        if (o instanceof IVersionProvider && activity instanceof AppCompatActivity) {
            final List<IVersion> versions = ((IVersionProvider)o).getVersions();
            for (final IVersion version : versions) {
                if (version.getAbbreviation().equals(CurrentSelected.getVersion())) {
                    mTranslationView.setText(version.getAbbreviation().toUpperCase());
                    break;
                }
            }
        } else if (o instanceof IVerseProvider) {
            mAdapter.updateVerses(((IVerseProvider) o).getVerses());

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
        if (CurrentSelected.getChapter() != null) {
            mRetriever.getVerses(CurrentSelected.getVersion(), String.valueOf(CurrentSelected.getBook()), String.valueOf(CurrentSelected.getChapter()));
        }
    }

    @Override
    public void onSelectionComplete(final int book, final int chapter, final int verse) {
        if (CurrentSelected.getChapter() != null) {
            mRetriever.getVerses(CurrentSelected.getVersion(), String.valueOf(CurrentSelected.getBook()), String.valueOf(CurrentSelected.getChapter()));
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
