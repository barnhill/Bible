package com.pnuema.bible.android.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.ListitemCopyrightBinding
import com.pnuema.bible.android.statics.HtmlUtils

class CopyrightViewHolder(
        parent: ViewGroup,
        private val binding: ListitemCopyrightBinding = ListitemCopyrightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {
    companion object {

        val type: Int
            get() = layout

        private val layout: Int
            get() = R.layout.listitem_copyright
    }

    fun bind(text: String) {
        binding.copyrightText.text = HtmlUtils.fromHtml(text)
    }
}
