package com.pnuema.simplebible.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Versions;

public class VersionSelectionViewHolder extends RecyclerView.ViewHolder {
    private final TextView mContentView;
    private Versions.Version mItem;

    public VersionSelectionViewHolder(View view) {
        super(view);
        mContentView = view.findViewById(R.id.version);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
    }

    @Nullable
    public TextView getContentView() {
        return mContentView;
    }

    @Nullable
    public Versions.Version getItem() {
        return mItem;
    }

    public void setItem(@NonNull Versions.Version mItem) {
        this.mItem = mItem;
    }
}