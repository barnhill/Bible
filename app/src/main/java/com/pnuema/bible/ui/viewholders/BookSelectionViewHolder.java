package com.pnuema.bible.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnuema.bible.R;
import com.pnuema.bible.data.IBook;

public class BookSelectionViewHolder extends RecyclerView.ViewHolder {
    private final TextView mContentView;
    private IBook mItem;

    public BookSelectionViewHolder(View view) {
        super(view);
        mContentView = view.findViewById(R.id.book);
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
    public IBook getItem() {
        return mItem;
    }

    public void setItem(@NonNull IBook mItem) {
        this.mItem = mItem;
    }
}
