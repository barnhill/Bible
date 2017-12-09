package com.pnuema.simplebible.ui.adapters;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.IBook;
import com.pnuema.simplebible.statics.CurrentSelected;
import com.pnuema.simplebible.ui.dialogs.BCVSelectionListener;
import com.pnuema.simplebible.ui.viewholders.BookSelectionViewHolder;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IBook} and makes a call to the
 * specified {@link BCVSelectionListener}.
 */
public class BookSelectionRecyclerViewAdapter extends RecyclerView.Adapter<BookSelectionViewHolder> {
    private final List<IBook> mValues;
    private final BCVSelectionListener mListener;

    public BookSelectionRecyclerViewAdapter(List<IBook> items, BCVSelectionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public BookSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookSelectionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_book, parent, false));
    }

    @Override
    public void onBindViewHolder(final BookSelectionViewHolder holder, int position) {
        TextView contentView = holder.getContentView();
        if (contentView == null) {
            return;
        }

        IBook book = mValues.get(position);

        holder.setItem(book);
        contentView.setText(book.getName());

        boolean currentIsSelected = CurrentSelected.getBook() != null && CurrentSelected.getBook().getId().equals(book.getId());

        contentView.setTextColor(ContextCompat.getColor(contentView.getContext(), currentIsSelected ? R.color.primary : R.color.primary_text));
        contentView.setTypeface(null, currentIsSelected ? Typeface.BOLD : Typeface.NORMAL);

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onBookSelected(holder.getItem());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
