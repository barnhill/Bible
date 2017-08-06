package com.pnuema.simplebible.ui.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Versions;
import com.pnuema.simplebible.retrievers.VersionsRetriever;
import com.pnuema.simplebible.statics.CurrentSelected;
import com.pnuema.simplebible.statics.LanguageUtils;
import com.pnuema.simplebible.ui.adapters.VersionSelectionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class VersionSelectionDialog extends DialogFragment implements VersionSelectionListener, Observer {
    private NotifyVersionSelectionCompleted mListener;
    private VersionSelectionRecyclerViewAdapter mAdapter;
    private final List<Versions.Version> mVersions = new ArrayList<>();
    private VersionsRetriever mRetriever = new VersionsRetriever();

    @Override
    public void onVersionSelected(Versions.Version version) {
        CurrentSelected.setVersion(version);
        if (mListener != null) {
            mListener.onSelectionComplete(CurrentSelected.getVersion());
        }
        dismiss();
    }

    public static VersionSelectionDialog instantiate(NotifyVersionSelectionCompleted notifySelectionCompleted) {
        VersionSelectionDialog dialog = new VersionSelectionDialog();
        dialog.setListener(notifySelectionCompleted);

        return dialog;
    }

    private VersionSelectionDialog setListener(NotifyVersionSelectionCompleted listener) {
        mListener = listener;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        mRetriever.loadData(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        mRetriever.deleteObservers();
    }

    @Override
    public void update(Observable observable, Object o) {
        mVersions.clear();
        if (o instanceof Versions && ((Versions)o).response != null && ((Versions)o).response.versions != null) {
            String lang = LanguageUtils.getISOLanguage();
            for (Versions.Version version : ((Versions)o).response.versions) {
                if (version.lang.contains(lang)) {
                    mVersions.add(version);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}