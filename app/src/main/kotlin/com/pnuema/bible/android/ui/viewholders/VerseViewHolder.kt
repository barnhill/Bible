package com.pnuema.bible.android.ui.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVerse
import com.pnuema.bible.android.databinding.ListitemVerseBinding
import com.pnuema.bible.android.statics.HtmlUtils
import com.pnuema.bible.android.ui.utils.VerseUtils.formatted

class VerseViewHolder(
        parent: ViewGroup,
        private val binding: ListitemVerseBinding = ListitemVerseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        val type: Int
            get() = layout

        private val layout: Int
            get() = R.layout.listitem_verse
    }

    fun bind(verse: IVerse) {
        binding.verseText.text = HtmlUtils.fromHtml(verse.formatted())
    }
}
