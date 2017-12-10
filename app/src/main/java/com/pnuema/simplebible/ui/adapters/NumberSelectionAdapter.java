package com.pnuema.simplebible.ui.adapters;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.ui.dialogs.NumberSelectionListener;
import com.pnuema.simplebible.ui.viewholders.NumberSelectionViewHolder;

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
                .inflate(R.layout.item_number, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = ((NumberSelectionViewHolder) holder).getTextView();

        if (textView == null) {
            return;
        }

        textView.setText(String.valueOf(position + 1));

        boolean currentIsSelected = mCurrentSelected != null && mCurrentSelected == position + 1;

        textView.setTextColor(ContextCompat.getColor(textView.getContext(), currentIsSelected ? R.color.primary : R.color.primary_text));
        textView.setTypeface(null, currentIsSelected ? Typeface.BOLD : Typeface.NORMAL);

        holder.itemView.setOnClickListener(v -> mOnClickListener.numberSelected(position));
    }

    @Override
    public int getItemCount() {
        return mMaxNumber;
    }
}
