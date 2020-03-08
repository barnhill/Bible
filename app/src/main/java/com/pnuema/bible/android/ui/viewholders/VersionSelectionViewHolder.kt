package com.pnuema.bible.android.ui.viewholders

import android.graphics.Typeface
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.dialogs.VersionSelectionListener

class VersionSelectionViewHolder(parent: ViewGroup, private val listener: VersionSelectionListener) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_version, parent, false)) {
    private val contentView: TextView = itemView as TextView

    override fun toString(): String {
        return super.toString() + " '" + contentView.text + "'"
    }

    fun bind(version: IVersion) {
        val contentView = contentView

        contentView.text = version.getDisplayText()

        val currentIsSelected = !TextUtils.isEmpty(CurrentSelected.version) && CurrentSelected.version == version.abbreviation

        contentView.setTextColor(ContextCompat.getColor(contentView.context, if (currentIsSelected) R.color.primary else R.color.primary_text))
        contentView.setTypeface(null, if (currentIsSelected) Typeface.BOLD else Typeface.NORMAL)

        itemView.setOnClickListener {
            listener.onVersionSelected(version.abbreviation)
        }
    }
}