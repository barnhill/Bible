package com.pnuema.android.bible.ui.viewholders;

import android.view.View;
import android.widget.TextView;

import com.pnuema.android.bible.R;
import com.pnuema.android.bible.ui.dialogs.NumberSelectionListener;

import androidx.recyclerview.widget.RecyclerView;

public final class NumberSelectionViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public NumberSelectionViewHolder(final View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textNumber);
    }

    public void bind(final int position, final boolean isSelected, final NumberSelectionListener onClickListener) {
        textView.setText(String.valueOf(position));

        textView.setTextAppearance(isSelected ? R.style.BookChapterVerse_NumberText_Selected : R.style.BookChapterVerse_NumberText);
        textView.setBackground(textView.getContext().getDrawable(isSelected ? R.drawable.selected_background_rounded : R.drawable.selectable_background_rounded));

        itemView.setOnClickListener(v -> onClickListener.numberSelected(position));
        textView.setOnClickListener(v -> onClickListener.numberSelected(position));
    }
}
