package com.pnuema.bible.ui.viewholders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnuema.bible.R;
import com.pnuema.bible.data.IVersion;

public class VersionSelectionViewHolder extends RecyclerView.ViewHolder {
    private final TextView mContentView;
    private IVersion mItem;

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
    public IVersion getItem() {
        return mItem;
    }

    public void setItem(@NonNull IVersion mItem) {
        this.mItem = mItem;
    }
}