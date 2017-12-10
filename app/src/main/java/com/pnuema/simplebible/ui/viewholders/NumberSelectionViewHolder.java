package com.pnuema.simplebible.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public final class NumberSelectionViewHolder extends RecyclerView.ViewHolder {
    public NumberSelectionViewHolder(View itemView) {
        super(itemView);
    }

    public TextView getTextView() {
        return (TextView) itemView;
    }
}
