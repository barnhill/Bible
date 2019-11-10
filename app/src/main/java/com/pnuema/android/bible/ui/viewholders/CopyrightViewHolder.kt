package com.pnuema.android.bible.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.statics.HtmlUtils

class CopyrightViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false)) {
    private val copyrightText: TextView = itemView.findViewById(R.id.copyrightText)

    companion object {

        val type: Int
            get() = layout

        private val layout: Int
            get() = R.layout.listitem_copyright
    }

    fun bind(text: String) {
        copyrightText.text = HtmlUtils.fromHtml(text)
    }
}
