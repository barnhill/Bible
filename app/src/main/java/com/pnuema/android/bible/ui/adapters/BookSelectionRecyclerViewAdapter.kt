package com.pnuema.android.bible.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.data.IBook
import com.pnuema.android.bible.data.firefly.Book
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener
import com.pnuema.android.bible.ui.viewholders.BookSelectionViewHolder

/**
 * [RecyclerView.Adapter] that can display a [Book] and makes a call to the
 * specified [BCVSelectionListener].
 */
class BookSelectionRecyclerViewAdapter(private val mValues: List<IBook>, private val mListener: BCVSelectionListener) : RecyclerView.Adapter<BookSelectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSelectionViewHolder {
        return BookSelectionViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_book, parent, false))
    }

    override fun onBindViewHolder(holder: BookSelectionViewHolder, position: Int) {
        holder.bind(mValues[position], mListener)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }
}
