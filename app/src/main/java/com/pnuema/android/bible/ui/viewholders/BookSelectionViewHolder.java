package com.pnuema.android.bible.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import com.pnuema.android.bible.R;
import com.pnuema.android.bible.data.IBook;
import com.pnuema.android.bible.statics.CurrentSelected;
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class BookSelectionViewHolder extends RecyclerView.ViewHolder {
    private final TextView mContentView;

    public BookSelectionViewHolder(final View view) {
        super(view);
        mContentView = view.findViewById(R.id.book);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
    }

    @Nullable
    public TextView getContentView() {
        return mContentView;
    }

    public void bind(@NonNull final IBook book, @NonNull final BCVSelectionListener listener) {
        final boolean isSelected = CurrentSelected.getBook() != null && book.getId() == CurrentSelected.getBook();

        mContentView.setTextAppearance(isSelected ? R.style.BookChapterVerse_BookText_Selected : R.style.BookChapterVerse_BookText);

        mContentView.setText(book.getName());

        itemView.setOnClickListener(v -> {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            listener.onBookSelected(book.getId());
        });
    }
}
