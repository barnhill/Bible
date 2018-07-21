package com.pnuema.bible.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnuema.bible.R;
import com.pnuema.bible.data.IVersion;
import com.pnuema.bible.data.IVersionProvider;
import com.pnuema.bible.retrievers.BaseRetriever;
import com.pnuema.bible.retrievers.DBTRetriever;
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
    private BaseRetriever mRetriever = new DBTRetriever(); //TODO have this select which retriever based on version

    @Override
    public void onVersionSelected(IVersion version) {
        CurrentSelected.setVersion(version);
        if (mListener != null) {
            mListener.onSelectionComplete(version);
        }
        dismiss();
    }

    public static VersionSelectionDialog instantiate(NotifyVersionSelectionCompleted notifySelectionCompleted) {
        VersionSelectionDialog dialog = new VersionSelectionDialog();
        dialog.setListener(notifySelectionCompleted);

        return dialog;
    }

    private void setListener(NotifyVersionSelectionCompleted listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_version_picker, container);

        mAdapter = new VersionSelectionRecyclerViewAdapter(mVersions, this);
        RecyclerView recyclerView = view.findViewById(R.id.versionRecyclerView);
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
    public void update(Observable observable, Object o) {
        mVersions.clear();
        if (o instanceof IVersionProvider && ((IVersionProvider)o).getVersions() != null) {
            String lang = LanguageUtils.getISOLanguage();
            for (IVersion version : ((IVersionProvider)o).getVersions()) {
                if (version.getLanguage().contains(lang)) {
                    mVersions.add(version);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}