package com.pnuema.bible.ui.adapters;

import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.bible.R;
import com.pnuema.bible.ui.dialogs.NumberSelectionListener;
import com.pnuema.bible.ui.viewholders.NumberSelectionViewHolder;

public final class NumberSelectionAdapter extends RecyclerView.Adapter {
    private int mMaxNumber;
    private NumberSelectionListener mOnClickListener;
    private Integer mCurrentSelected;

    public NumberSelectionAdapter(final int maxNumber, final Integer currentSelected, final NumberSelectionListener onClickListener) {
        mMaxNumber = maxNumber;
        mOnClickListener = onClickListener;
        mCurrentSelected = currentSelected;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new NumberSelectionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_number, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final TextView textView = ((NumberSelectionViewHolder) holder).getTextView();

        if (textView == null) {
            return;
        }

        textView.setText(String.valueOf(position + 1));

        final boolean currentIsSelected = mCurrentSelected != null && mCurrentSelected == position + 1;

        textView.setTextColor(ContextCompat.getColor(textView.getContext(), currentIsSelected ? R.color.white : R.color.primary_text));
        textView.setTypeface(null, currentIsSelected ? Typeface.BOLD : Typeface.NORMAL);
        textView.setBackground(textView.getContext().getDrawable(currentIsSelected ? R.drawable.selected_background_rounded : R.drawable.selectable_background_rounded));

        holder.itemView.setOnClickListener(v -> mOnClickListener.numberSelected(position + 1));
    }

    @Override
    public int getItemCount() {
        return mMaxNumber;
    }
}
