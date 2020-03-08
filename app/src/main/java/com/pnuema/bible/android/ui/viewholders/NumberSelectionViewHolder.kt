package com.pnuema.bible.android.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.ui.dialogs.NumberSelectionListener

class NumberSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textView: TextView = itemView.findViewById(R.id.textNumber)

    fun bind(position: Int, isSelected: Boolean, onClickListener: NumberSelectionListener) {
        textView.text = position.toString()

        textView.setTextAppearance(if (isSelected) R.style.BookChapterVerse_NumberText_Selected else R.style.BookChapterVerse_NumberText)
        textView.background = textView.context.getDrawable(if (isSelected) R.drawable.selected_background_rounded else R.drawable.selectable_background_rounded)

        itemView.setOnClickListener { onClickListener.numberSelected(position) }
        textView.setOnClickListener { onClickListener.numberSelected(position) }
    }
}
