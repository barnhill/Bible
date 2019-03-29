package com.pnuema.android.bible.ui.viewholders;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnuema.android.bible.R;
import com.pnuema.android.bible.data.IBook;

public class BookSelectionViewHolder extends RecyclerView.ViewHolder {
    private final TextView mContentView;
    private IBook mItem;

    public BookSelectionViewHolder(final View view) {
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

    public void setItem(@NonNull final IBook mItem) {
        this.mItem = mItem;
    }
}
