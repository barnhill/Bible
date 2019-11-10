package com.pnuema.android.bible.ui.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.android.bible.R;
import com.pnuema.android.bible.statics.HtmlUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CopyrightViewHolder extends RecyclerView.ViewHolder {
    private TextView copyrightText;

    public CopyrightViewHolder(@NonNull final ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false));

        copyrightText = itemView.findViewById(R.id.copyrightText);
    }

    public void bind(final String text) {
        copyrightText.setText(HtmlUtils.fromHtml(text));
    }

    public static int getType() {
        return getLayout();
    }

    private static int getLayout(){
        return R.layout.listitem_copyright;
    }
}
