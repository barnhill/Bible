package com.pnuema.bible.ui.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.bible.R;
import com.pnuema.bible.statics.HtmlUtils;

public class CopyrightViewHolder extends RecyclerView.ViewHolder {
    private TextView copyrightText;

    public CopyrightViewHolder(@NonNull ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false));

        copyrightText = itemView.findViewById(R.id.copyrightText);
    }

    public void setText(String text) {
        copyrightText.setText(HtmlUtils.fromHtml(text));
    }

    public static int getType() {
        return getLayout();
    }

    private static int getLayout(){
        return R.layout.listitem_copyright;
    }
}
