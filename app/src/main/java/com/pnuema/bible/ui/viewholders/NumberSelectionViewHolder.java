package com.pnuema.bible.ui.viewholders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnuema.bible.R;

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
