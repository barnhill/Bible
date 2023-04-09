package com.pnuema.bible.android.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.ui.viewholders.CopyrightViewHolder
import com.pnuema.bible.android.ui.viewholders.VerseViewHolder
import com.pnuema.bible.android.ui.viewstates.VerseViewState

class VersesAdapter : ListAdapter<VerseViewState, RecyclerView.ViewHolder>(
    object : ItemCallback<VerseViewState>() {
        override fun areItemsTheSame(oldItem: VerseViewState, newItem: VerseViewState): Boolean = false
        override fun areContentsTheSame(oldItem: VerseViewState, newItem: VerseViewState): Boolean = false
    }
) {
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
            is VerseViewHolder -> holder.bind(currentList[position])
            is CopyrightViewHolder -> holder.bind(copyrightText)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position < currentList.size -> VerseViewHolder.type
            position == currentList.size -> CopyrightViewHolder.type
            else -> -1
        }
    }

    override fun getItemCount(): Int {
        return currentList.size + if (!::copyrightText.isInitialized) 0 else 1 //account for the copyright item
    }
}
