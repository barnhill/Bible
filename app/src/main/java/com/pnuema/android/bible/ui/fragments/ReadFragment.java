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
import com.pnuema.android.bible.data.IVersion;
import com.pnuema.android.bible.retrievers.FireflyRetriever;
import com.pnuema.android.bible.statics.CurrentSelected;
import com.pnuema.android.bible.ui.adapters.VersesAdapter;
import com.pnuema.android.bible.ui.dialogs.BCVDialog;
import com.pnuema.android.bible.ui.dialogs.NotifySelectionCompleted;
import com.pnuema.android.bible.ui.dialogs.NotifyVersionSelectionCompleted;
import com.pnuema.android.bible.ui.fragments.viewModel.ReadViewModel;
import com.pnuema.android.bible.ui.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The reading pane fragment
 */
public class ReadFragment extends Fragment implements NotifySelectionCompleted {
    private VersesAdapter mAdapter;
    private TextView mBookChapterView;
    private TextView mTranslationView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.SmoothScroller mSmoothScroller;
    private List<IBook> books = new ArrayList<>();
    private ReadViewModel viewModel;

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

        viewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        viewModel.getLiveVersions().observe(getViewLifecycleOwner(), iVersionProvider -> {
            final List<IVersion> versions = iVersionProvider.getVersions();
            for (final IVersion version : versions) {
                if (version.getAbbreviation().equals(CurrentSelected.getVersion())) {
                    mTranslationView.setText(version.getAbbreviation().toUpperCase());
                    break;
                }
            }
        });

        viewModel.getLiveBook().observe(getViewLifecycleOwner(), iBookProvider -> {
            books.clear();
            books.addAll(iBookProvider.getBooks());
            setBookChapterText();
        });

        viewModel.getLiveVerses().observe(getViewLifecycleOwner(), iVerseProvider -> {
            mAdapter.updateVerses(iVerseProvider.getVerses());

            final Integer verse = CurrentSelected.getVerse();
            if (verse != null) {
                new Handler().post(() -> scrollToVerse(verse));
            }

            setBookChapterText();
        });

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

        refresh();
    }

    public void refresh() {
        viewModel.load();
    }

    @Override
    public void onSelectionPreloadChapter(final int book, final int chapter) {
        if (CurrentSelected.getChapter() != null) {
            FireflyRetriever.Companion.get().getVerses(CurrentSelected.getVersion(), String.valueOf(CurrentSelected.getBook()), String.valueOf(CurrentSelected.getChapter()));
        }
    }

    @Override
    public void onSelectionComplete(final int book, final int chapter, final int verse) {
        if (CurrentSelected.getChapter() != null) {
            viewModel.load();
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
