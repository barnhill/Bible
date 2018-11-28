package com.pnuema.bible.ui.dialogs;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnuema.bible.R;
import com.pnuema.bible.data.IVersion;
import com.pnuema.bible.data.firefly.Versions;
import com.pnuema.bible.retrievers.BaseRetriever;
import com.pnuema.bible.retrievers.FireflyRetriever;
import com.pnuema.bible.statics.CurrentSelected;
import com.pnuema.bible.statics.LanguageUtils;
import com.pnuema.bible.ui.adapters.VersionSelectionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class VersionSelectionDialog extends DialogFragment implements VersionSelectionListener, Observer {
    private NotifyVersionSelectionCompleted mListener;
    private VersionSelectionRecyclerViewAdapter mAdapter;
    private final List<IVersion> mVersions = new ArrayList<>();
    private BaseRetriever mRetriever = new FireflyRetriever();

    @Override
    public void onVersionSelected(final String version) {
        CurrentSelected.setVersion(version);
        if (mListener != null) {
            mListener.onSelectionComplete(version);
        }
        dismiss();
    }

    public static VersionSelectionDialog instantiate(final NotifyVersionSelectionCompleted notifySelectionCompleted) {
        final VersionSelectionDialog dialog = new VersionSelectionDialog();
        dialog.setListener(notifySelectionCompleted);

        return dialog;
    }

    private void setListener(final NotifyVersionSelectionCompleted listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_version_picker, container);

        mAdapter = new VersionSelectionRecyclerViewAdapter(mVersions, this);
        final RecyclerView recyclerView = view.findViewById(R.id.versionRecyclerView);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRetriever.addObserver(this);
        mRetriever.getVersions();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRetriever.deleteObservers();
    }

    @Override
    public void update(final Observable observable, final Object o) {
        mVersions.clear();
        if (o instanceof Versions) {
            final String lang = LanguageUtils.getISOLanguage();
            for (final IVersion version : ((Versions)o).getVersions()) {
                if (version.getLanguage().contains(lang)) {
                    mVersions.add(version);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}