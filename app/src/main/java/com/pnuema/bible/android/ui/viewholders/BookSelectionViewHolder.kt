package com.pnuema.bible.android.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_book.view.*

class BookSelectionViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
    override fun toString(): String {
        return super.toString() + " '" + itemView.book.text + "'"
    }

    override val containerView: View
        get() = itemView

    fun bind(book: IBook, listener: BCVSelectionListener) {
        itemView.book?:return
        val isSelected = CurrentSelected.book != CurrentSelected.DEFAULT_VALUE && book.getId() == CurrentSelected.book

        itemView.book.setTextAppearance(if (isSelected) R.style.BookChapterVerse_BookText_Selected else R.style.BookChapterVerse_BookText)

        itemView.book.text = book.getName()

        itemView.setOnClickListener {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            listener.onBookSelected(book.getId())
        }
    }
}
