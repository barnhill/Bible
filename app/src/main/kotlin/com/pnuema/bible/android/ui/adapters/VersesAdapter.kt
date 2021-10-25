package com.pnuema.bible.android.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.ui.viewholders.CopyrightViewHolder
import com.pnuema.bible.android.ui.viewholders.VerseViewHolder

class VersesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val verses = ArrayList<IVerse>()
    private lateinit var copyrightText: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VerseViewHolder.type -> VerseViewHolder(parent)
            CopyrightViewHolder.type -> CopyrightViewHolder(parent)
            else -> throw IllegalArgumentException("No view holder that matches the requested type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VerseViewHolder -> holder.bind(verses[position])
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
            //TODO show copyright text
        }*/

        //diffutils doesnt work here because every list shown is a new list so the animations look weird
        notifyDataSetChanged()
    }
}
