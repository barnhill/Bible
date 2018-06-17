package com.pnuema.simplebible.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.pnuema.simplebible.data.IVerse;
import com.pnuema.simplebible.ui.viewholders.CopyrightViewHolder;
import com.pnuema.simplebible.ui.viewholders.VerseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class VersesAdapter extends RecyclerView.Adapter {
    private List<IVerse> mVerses = new ArrayList<>();
    private String copyrightText;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VerseViewHolder.getType()) {
            return new VerseViewHolder(parent);
        } else if (viewType == CopyrightViewHolder.getType()) {
            return new CopyrightViewHolder(parent);
        }

        throw new IllegalArgumentException("No viewholder that matches the requested type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VerseViewHolder) {
            ((VerseViewHolder) holder).setVerseText(mVerses.get(position).getText(holder.itemView.getContext()));
        } else if (holder instanceof CopyrightViewHolder) {
            ((CopyrightViewHolder) holder).setText(copyrightText);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mVerses.size()) {
            return VerseViewHolder.getType();
        } else if (position == mVerses.size()) {
            return CopyrightViewHolder.getType();
        }

        return -1;
    }

    @Override
    public int getItemCount() {
        return mVerses.size() + (copyrightText == null ? 0 : 1); //account for the copyright item
    }

    public void updateVerses(List<IVerse> verses) {
        mVerses.clear();
        mVerses.addAll(verses);

        if (!verses.isEmpty()) {
            copyrightText = verses.get(0).getCopyright();
        }

        notifyDataSetChanged();
    }
}
