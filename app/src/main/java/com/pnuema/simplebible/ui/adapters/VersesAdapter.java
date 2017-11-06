package com.pnuema.simplebible.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.IVerse;
import com.pnuema.simplebible.ui.viewholders.VerseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class VersesAdapter extends RecyclerView.Adapter {
    private List<IVerse> mVerses = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VerseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_verse, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VerseViewHolder verseViewHolder = null;
        if (holder instanceof VerseViewHolder) {
            verseViewHolder = (VerseViewHolder) holder;
        }

        if (verseViewHolder != null) {
            verseViewHolder.getVerseText().setText(mVerses.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return mVerses.size();
    }

    public void updateVerses(List<IVerse> verses) {
        mVerses.clear();
        mVerses.addAll(verses);
        notifyDataSetChanged();
    }
}
