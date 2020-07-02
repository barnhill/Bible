package com.pnuema.bible.android.ui.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.statics.HtmlUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.listitem_copyright.view.*

class CopyrightViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false)), LayoutContainer {
    companion object {

        val type: Int
            get() = layout

        private val layout: Int
            get() = R.layout.listitem_copyright
    }

    override val containerView: View?
        get() = itemView

    fun bind(text: String) {
        itemView.copyrightText.text = HtmlUtils.fromHtml(text)
    }
}
