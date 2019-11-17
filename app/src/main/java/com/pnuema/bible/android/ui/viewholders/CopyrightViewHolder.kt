package com.pnuema.bible.android.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.statics.HtmlUtils

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
