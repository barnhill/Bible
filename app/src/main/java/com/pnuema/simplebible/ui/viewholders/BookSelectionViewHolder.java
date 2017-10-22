package com.pnuema.simplebible.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Books;

public class BookSelectionViewHolder extends RecyclerView.ViewHolder {
    private final TextView mContentView;
    private Books.Book mItem;

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
    public Books.Book getItem() {
        return mItem;
    }

    public void setItem(@NonNull Books.Book mItem) {
        this.mItem = mItem;
    }
}
