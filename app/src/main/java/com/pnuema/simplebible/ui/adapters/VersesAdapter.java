package com.pnuema.simplebible.ui.adapters;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.Verses;
import com.pnuema.simplebible.ui.viewholders.VerseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class VersesAdapter extends RecyclerView.Adapter {
    private List<Verses.Verse> mVerses = new ArrayList<>();

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
            Spanned spannedString = fromHtml(htmlFormatting(mVerses.get(position).text));
            verseViewHolder.verseText.setText(spannedString);
        }
    }

    @Override
    public int getItemCount() {
        return mVerses.size();
    }

    public void updateVerses(List<Verses.Verse> verses) {
        mVerses.clear();
        mVerses.addAll(verses);
        notifyDataSetChanged();
    }

    @SuppressWarnings("deprecation")
    private static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    //TODO finish formatting html prior to calling fromHtml
    private static String htmlFormatting(String html) {
        String formatted;
        formatted = html.replaceAll("<p class=\"p\">", "").replaceAll("</p>", "");
        return formatted;
    }
}
