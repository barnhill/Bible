package com.pnuema.android.bible.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.data.IBook
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener

class BookSelectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val contentView: TextView? = view.findViewById(R.id.book)

    override fun toString(): String {
        return super.toString() + " '" + contentView!!.text + "'"
    }

    fun bind(book: IBook, listener: BCVSelectionListener) {
        val isSelected = CurrentSelected.book != null && book.id == CurrentSelected.book

        contentView!!.setTextAppearance(if (isSelected) R.style.BookChapterVerse_BookText_Selected else R.style.BookChapterVerse_BookText)

        contentView.text = book.name

        itemView.setOnClickListener { v ->
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            listener.onBookSelected(book.id)
        }
    }
}
