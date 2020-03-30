package com.pnuema.bible.android.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.statics.HtmlUtils
import com.pnuema.bible.android.ui.viewholders.CopyrightViewHolder
import com.pnuema.bible.android.ui.viewholders.VerseViewHolder

class VersesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val verses = ArrayList<IVerse>()
    private lateinit var copyrightText: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VerseViewHolder.type -> VerseViewHolder(parent)
            CopyrightViewHolder.type -> CopyrightViewHolder(parent)
            else -> throw IllegalArgumentException("No viewholder that matches the requested type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VerseViewHolder -> holder.bind(HtmlUtils.fromHtml(verses[position].getText()))
            is CopyrightViewHolder -> holder.bind(copyrightText)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position < verses.size -> VerseViewHolder.type
            position == verses.size -> CopyrightViewHolder.type
            else -> -1
        }
    }

    override fun getItemCount(): Int {
        return verses.size + if (!::copyrightText.isInitialized) 0 else 1 //account for the copyright item
    }

    fun updateVerses(verses: List<IVerse>) {
        this.verses.clear()
        this.verses.addAll(verses)

        /* if (!verses.isEmpty()) {
            copyrightText = verses.get(0).getCopyright();
        }*/ //TODO show copyright text

        notifyDataSetChanged()
    }
}
