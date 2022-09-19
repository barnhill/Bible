package com.pnuema.bible.android.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.data.firefly.Book
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.viewholders.BookSelectionViewHolder
import com.pnuema.bible.android.ui.viewstates.BookViewState

/**
 * [RecyclerView.Adapter] that can display a [Book] and makes a call to the
 * specified [BCVSelectionListener].
 */
class BookSelectionRecyclerViewAdapter(
    private val mListener: BCVSelectionListener
) : ListAdapter<BookViewState, BookSelectionViewHolder>(
    object : ItemCallback<BookViewState>() {
        override fun areItemsTheSame(oldItem: BookViewState, newItem: BookViewState): Boolean = oldItem.javaClass == newItem.javaClass
        override fun areContentsTheSame(oldItem: BookViewState, newItem: BookViewState): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSelectionViewHolder = BookSelectionViewHolder(parent)
    override fun onBindViewHolder(holder: BookSelectionViewHolder, position: Int) = holder.bind(currentList[position], mListener)
}
