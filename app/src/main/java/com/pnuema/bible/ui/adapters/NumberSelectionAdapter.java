package com.pnuema.bible.ui.adapters;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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

    public NumberSelectionAdapter(int maxNumber, Integer currentSelected, NumberSelectionListener onClickListener) {
        mMaxNumber = maxNumber;
        mOnClickListener = onClickListener;
        mCurrentSelected = currentSelected;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NumberSelectionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_number, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = ((NumberSelectionViewHolder) holder).getTextView();

        if (textView == null) {
            return;
        }

        textView.setText(String.valueOf(position + 1));

        boolean currentIsSelected = mCurrentSelected != null && mCurrentSelected == position + 1;

        textView.setTextColor(ContextCompat.getColor(textView.getContext(), currentIsSelected ? R.color.white : R.color.primary_text));
        textView.setTypeface(null, currentIsSelected ? Typeface.BOLD : Typeface.NORMAL);
        textView.setBackground(textView.getContext().getDrawable(currentIsSelected ? R.drawable.selected_background_rounded : R.drawable.selectable_background_rounded));

        holder.itemView.setOnClickListener(v -> mOnClickListener.numberSelected(position));
    }

    @Override
    public int getItemCount() {
        return mMaxNumber;
    }
}
