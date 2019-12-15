package com.pnuema.bible.android.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener

class BookSelectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val contentView: TextView? = view.findViewById(R.id.book)

    override fun toString(): String {
        return super.toString() + " '" + contentView!!.text + "'"
    }

    fun bind(book: IBook, listener: BCVSelectionListener) {
        val isSelected = CurrentSelected.book != null && book.getId() == CurrentSelected.book

        contentView!!.setTextAppearance(if (isSelected) R.style.BookChapterVerse_BookText_Selected else R.style.BookChapterVerse_BookText)

        contentView.text = book.getName()

        itemView.setOnClickListener {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            listener.onBookSelected(book.getId())
        }
    }
}
