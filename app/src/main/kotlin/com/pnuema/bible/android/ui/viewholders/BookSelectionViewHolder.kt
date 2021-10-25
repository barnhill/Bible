package com.pnuema.bible.android.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IBook
import com.pnuema.bible.android.databinding.ListitemBookBinding
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener

class BookSelectionViewHolder(
        parent: ViewGroup,
        private val binding: ListitemBookBinding = ListitemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {

    override fun toString(): String {
        return super.toString() + " '" + binding.book.text + "'"
    }

    fun bind(book: IBook, listener: BCVSelectionListener) {
        val isSelected = CurrentSelected.book != CurrentSelected.DEFAULT_VALUE && book.getId() == CurrentSelected.book

        binding.book.setTextAppearance(if (isSelected) R.style.BookChapterVerse_BookText_Selected else R.style.BookChapterVerse_BookText)

        binding.book.text = book.getName()

        itemView.setOnClickListener {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            listener.onBookSelected(book.getId())
        }
    }
}
