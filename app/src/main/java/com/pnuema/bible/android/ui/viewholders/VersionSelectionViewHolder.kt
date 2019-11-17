package com.pnuema.bible.android.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVersion

class VersionSelectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val contentView: TextView? = view.findViewById(R.id.version)
    var item: IVersion? = null

    override fun toString(): String {
        return super.toString() + " '" + contentView!!.text + "'"
    }
}