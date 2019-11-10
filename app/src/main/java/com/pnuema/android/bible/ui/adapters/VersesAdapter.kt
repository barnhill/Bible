package com.pnuema.android.bible.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.data.IVerse
import com.pnuema.android.bible.statics.HtmlUtils
import com.pnuema.android.bible.ui.viewholders.CopyrightViewHolder
import com.pnuema.android.bible.ui.viewholders.VerseViewHolder
import java.util.*

class VersesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mVerses = ArrayList<IVerse>()
    private val copyrightText: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VerseViewHolder.type) {
            return VerseViewHolder(parent)
        } else if (viewType == CopyrightViewHolder.type) {
            return CopyrightViewHolder(parent)
        }

        throw IllegalArgumentException("No viewholder that matches the requested type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is VerseViewHolder) {
            holder.bind(HtmlUtils.fromHtml(mVerses[position].text))
        } else if (holder is CopyrightViewHolder) {
            holder.bind(copyrightText!!)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < mVerses.size) {
            return VerseViewHolder.type
        } else if (position == mVerses.size) {
            return CopyrightViewHolder.type
        }

        return -1
    }

    override fun getItemCount(): Int {
        return mVerses.size + if (copyrightText == null) 0 else 1 //account for the copyright item
    }

    fun updateVerses(verses: List<IVerse>) {
        mVerses.clear()
        mVerses.addAll(verses)

        /* if (!verses.isEmpty()) {
            copyrightText = verses.get(0).getCopyright();
        }*/ //TODO show copyright text

        notifyDataSetChanged()
    }
}
