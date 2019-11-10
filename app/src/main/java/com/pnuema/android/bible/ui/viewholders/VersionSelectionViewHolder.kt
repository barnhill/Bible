package com.pnuema.android.bible.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.data.IVersion

class VersionSelectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val contentView: TextView? = view.findViewById(R.id.version)
    var item: IVersion? = null

    override fun toString(): String {
        return super.toString() + " '" + contentView!!.text + "'"
    }
}