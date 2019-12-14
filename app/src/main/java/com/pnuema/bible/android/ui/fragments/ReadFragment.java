package com.pnuema.bible.android.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.bible.android.R;
import com.pnuema.bible.android.data.IBook;
import com.pnuema.bible.android.data.IVersion;
import com.pnuema.bible.android.retrievers.FireflyRetriever;
import com.pnuema.bible.android.statics.CurrentSelected;
import com.pnuema.bible.android.ui.adapters.VersesAdapter;
import com.pnuema.bible.android.ui.dialogs.BCVDialog;
import com.pnuema.bible.android.ui.dialogs.NotifySelectionCompleted;
import com.pnuema.bible.android.ui.dialogs.NotifyVersionSelectionCompleted;
import com.pnuema.bible.android.ui.fragments.viewModel.ReadViewModel;
import com.pnuema.bible.android.ui.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private VersesAdapter adapter;
    private TextView bookChapterView;
    private TextView translationView;
    private RecyclerView.LayoutManager layoutManager;
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
        final View view = inflater.inflate(R.layout.fragment_read, container);
        final RecyclerView recyclerView = view.findViewById(R.id.versesRecyclerView);
        layoutManager = recyclerView.getLayoutManager();
        adapter = new VersesAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        bookChapterView = getActivity().findViewById(R.id.selected_book);
        translationView = getActivity().findViewById(R.id.selected_translation);

        viewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        viewModel.getLiveVersions().observe(getViewLifecycleOwner(), iVersionProvider -> {
            final List<IVersion> versions = iVersionProvider.getVersions();
            for (final IVersion version : versions) {
                if (version.getAbbreviation().equals(CurrentSelected.INSTANCE.getVersion())) {
                    translationView.setText(version.getAbbreviation().toUpperCase());
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
            adapter.updateVerses(iVerseProvider.getVerses());

            final Integer verse = CurrentSelected.INSTANCE.getVerse();
            new Handler().post(() -> scrollToVerse(verse));

            setBookChapterText();
        });

        setAppBarDisplay();
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
        FireflyRetriever.Companion.get().getVerses(CurrentSelected.INSTANCE.getVersion(), String.valueOf(CurrentSelected.INSTANCE.getBook()), String.valueOf(CurrentSelected.INSTANCE.getChapter()));
    }

    @Override
    public void onSelectionComplete(final int book, final int chapter, final int verse) {
        viewModel.load();
    }

    private void scrollToVerse(@Nullable final Integer verse) {
        if (verse == null) {
            return;
        }

        final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(Objects.requireNonNull(getActivity())) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        smoothScroller.setTargetPosition(verse - 1);
        layoutManager.startSmoothScroll(smoothScroller);
    }

    private void setAppBarDisplay() {
        if (!isAdded() || getActivity() == null) {
            return;
        }
        if (bookChapterView != null) {
            setBookChapterText();
            bookChapterView.setOnClickListener(view -> DialogUtils.INSTANCE.showBookChapterVersePicker(getActivity(), BCVDialog.BCV.BOOK, ReadFragment.this));
        }

        if (translationView != null) {
            translationView.setOnClickListener(view -> DialogUtils.INSTANCE.showVersionsPicker(getActivity(), (NotifyVersionSelectionCompleted) getActivity()));
        }
    }

    private void setBookChapterText() {
        if (bookChapterView != null) {
            for (final IBook book : books) {
                if (CurrentSelected.INSTANCE.getBook() != null && book.getId() == CurrentSelected.INSTANCE.getBook()) {
                    bookChapterView.setText(getString(R.string.book_chapter_header_format, book.getName(), CurrentSelected.INSTANCE.getChapter()));
                }
            }
        }
    }
}
