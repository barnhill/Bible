package com.pnuema.android.bible.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R

class VerseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false)) {
    private val verseText: TextView = itemView.findViewById(R.id.verseText)

    companion object {
        val type: Int
            get() = layout

        private val layout: Int
            get() = R.layout.listitem_verse
    }

    fun bind(text: CharSequence) {
        verseText.text = text
    }
}
