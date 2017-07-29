package com.pnuema.simplebible.ui.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnuema.simplebible.R;

public class VerseViewHolder extends RecyclerView.ViewHolder {
    public TextView verseText;
    public VerseViewHolder(View itemView) {
        super(itemView);

        verseText = itemView.findViewById(R.id.verseText);
    }
}