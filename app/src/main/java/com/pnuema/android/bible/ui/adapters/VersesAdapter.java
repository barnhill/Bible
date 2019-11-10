package com.pnuema.android.bible.ui.adapters;

import android.view.ViewGroup;

import com.pnuema.android.bible.data.IVerse;
import com.pnuema.android.bible.statics.HtmlUtils;
import com.pnuema.android.bible.ui.viewholders.CopyrightViewHolder;
import com.pnuema.android.bible.ui.viewholders.VerseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VersesAdapter extends RecyclerView.Adapter {
    private List<IVerse> mVerses = new ArrayList<>();
    private String copyrightText;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        if (viewType == VerseViewHolder.getType()) {
            return new VerseViewHolder(parent);
        } else if (viewType == CopyrightViewHolder.getType()) {
            return new CopyrightViewHolder(parent);
        }

        throw new IllegalArgumentException("No viewholder that matches the requested type");
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VerseViewHolder) {
            ((VerseViewHolder) holder).bind(HtmlUtils.fromHtml(mVerses.get(position).getText()));
        } else if (holder instanceof CopyrightViewHolder) {
            ((CopyrightViewHolder) holder).bind(copyrightText);
        }
    }

    @Override
    public int getItemViewType(final int position) {
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

    public void updateVerses(final List<IVerse> verses) {
        mVerses.clear();
        mVerses.addAll(verses);

       /* if (!verses.isEmpty()) {
            copyrightText = verses.get(0).getCopyright();
        }*/ //TODO show copyright text

        notifyDataSetChanged();
    }
}
