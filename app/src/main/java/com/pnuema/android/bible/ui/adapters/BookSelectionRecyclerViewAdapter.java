package com.pnuema.android.bible.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.android.bible.R;
import com.pnuema.android.bible.data.IBook;
import com.pnuema.android.bible.data.firefly.Book;
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener;
import com.pnuema.android.bible.ui.viewholders.BookSelectionViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Book} and makes a call to the
 * specified {@link BCVSelectionListener}.
 */
public class BookSelectionRecyclerViewAdapter extends RecyclerView.Adapter<BookSelectionViewHolder> {
    private final List<IBook> mValues;
    private final BCVSelectionListener mListener;

    public BookSelectionRecyclerViewAdapter(final List<IBook> items, final BCVSelectionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public BookSelectionViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return new BookSelectionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_book, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final BookSelectionViewHolder holder, final int position) {
        final TextView contentView = holder.getContentView();
        if (contentView == null) {
            return;
        }

        holder.bind(mValues.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
