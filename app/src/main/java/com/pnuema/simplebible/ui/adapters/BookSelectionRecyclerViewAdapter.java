package com.pnuema.simplebible.ui.adapters;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Books;
import com.pnuema.simplebible.statics.CurrentSelected;
import com.pnuema.simplebible.ui.dialogs.BCVSelectionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link com.pnuema.simplebible.data.Books.Book} and makes a call to the
 * specified {@link BCVSelectionListener}.
 */
public class BookSelectionRecyclerViewAdapter extends RecyclerView.Adapter<BookSelectionRecyclerViewAdapter.ViewHolder> {
    private final List<Books.Book> mValues;
    private final BCVSelectionListener mListener;

    public BookSelectionRecyclerViewAdapter(List<Books.Book> items, BCVSelectionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_book, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).name);

        boolean currentIsSelected = CurrentSelected.getBook() != null && CurrentSelected.getBook().id.equals(mValues.get(position).id);

        holder.mContentView.setTextColor(ContextCompat.getColor(holder.mContentView.getContext(), currentIsSelected ? R.color.primary : R.color.primary_text));
        holder.mContentView.setTypeface(null, currentIsSelected ? Typeface.BOLD : Typeface.NORMAL);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onBookSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mContentView;
        Books.Book mItem;

        ViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.book);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
