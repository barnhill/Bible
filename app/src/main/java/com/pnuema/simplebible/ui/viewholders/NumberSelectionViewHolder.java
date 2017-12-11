package com.pnuema.simplebible.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnuema.simplebible.R;

public final class NumberSelectionViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public NumberSelectionViewHolder(View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textNumber);
    }

    public TextView getTextView() {
        return textView;
    }
}
