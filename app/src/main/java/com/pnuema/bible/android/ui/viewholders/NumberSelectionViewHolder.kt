package com.pnuema.bible.android.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.ui.dialogs.NumberSelectionListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_number.view.*

class NumberSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
    override val containerView: View?
        get() = itemView

    fun bind(position: Int, isSelected: Boolean, onClickListener: NumberSelectionListener) {
        itemView.textNumber.text = position.toString()

        itemView.textNumber.setTextAppearance(if (isSelected) R.style.BookChapterVerse_NumberText_Selected else R.style.BookChapterVerse_NumberText)
        itemView.textNumber.background = itemView.textNumber.context.getDrawable(if (isSelected) R.drawable.selected_background_rounded else R.drawable.selectable_background_rounded)

        itemView.setOnClickListener { onClickListener.numberSelected(position) }
        itemView.textNumber.setOnClickListener { onClickListener.numberSelected(position) }
    }
}
