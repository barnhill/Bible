package com.pnuema.android.bible.ui.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.android.bible.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerseViewHolder extends RecyclerView.ViewHolder {
    private TextView verseText;

    public VerseViewHolder(@NonNull final ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false));

        verseText = itemView.findViewById(R.id.verseText);
    }

    public void bind(final CharSequence text) {
        verseText.setText(text);
    }

    public static int getType() {
        return getLayout();
    }

    private static int getLayout() {
        return R.layout.listitem_verse;
    }
}
