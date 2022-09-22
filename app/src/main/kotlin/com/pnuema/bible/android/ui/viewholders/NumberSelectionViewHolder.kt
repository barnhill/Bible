package com.pnuema.bible.android.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.ListitemNumberBinding
import com.pnuema.bible.android.ui.dialogs.NumberSelectionListener

class NumberSelectionViewHolder(
        parent: ViewGroup,
        private val onClickListener: NumberSelectionListener,
        private val binding: ListitemNumberBinding = ListitemNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false),
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int, isSelected: Boolean) {
        binding.textNumber.text = position.toString()

        binding.textNumber.setTextAppearance(if (isSelected) R.style.BookChapterVerse_NumberText_Selected else R.style.BookChapterVerse_NumberText)
        binding.textNumber.background = ContextCompat.getDrawable(binding.root.context, if (isSelected) R.drawable.selected_background_rounded else R.drawable.selectable_background_rounded)

        binding.root.setOnClickListener { onClickListener.numberSelected(position) }
        binding.textNumber.setOnClickListener { onClickListener.numberSelected(position) }
    }
}
