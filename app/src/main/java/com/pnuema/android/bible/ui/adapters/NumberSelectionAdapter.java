package com.pnuema.android.bible.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pnuema.android.bible.R;
import com.pnuema.android.bible.ui.dialogs.NumberSelectionListener;
import com.pnuema.android.bible.ui.viewholders.NumberSelectionViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public final class NumberSelectionAdapter extends RecyclerView.Adapter<NumberSelectionViewHolder> {
    private int mMaxNumber;
    private NumberSelectionListener mOnClickListener;
    private Integer mCurrentSelected;

    public NumberSelectionAdapter(final int maxNumber, final Integer currentSelected, final NumberSelectionListener onClickListener) {
        mMaxNumber = maxNumber;
        mOnClickListener = onClickListener;
        mCurrentSelected = currentSelected;
    }

    @Override
    public NumberSelectionViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new NumberSelectionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_number, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final NumberSelectionViewHolder holder, final int position) {
        final int pos = position + 1;
        holder.bind(pos, mCurrentSelected != null && mCurrentSelected == pos, mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mMaxNumber;
    }
}
