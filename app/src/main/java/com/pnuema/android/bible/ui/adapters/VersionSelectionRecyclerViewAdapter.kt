package com.pnuema.android.bible.ui.adapters

import android.graphics.Typeface
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.data.IVersion
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener
import com.pnuema.android.bible.ui.dialogs.VersionSelectionListener
import com.pnuema.android.bible.ui.viewholders.VersionSelectionViewHolder

/**
 * [RecyclerView.Adapter] that can display a [IVersion] and makes a call to the
 * specified [BCVSelectionListener].
 */
class VersionSelectionRecyclerViewAdapter(private val mValues: List<IVersion>, private val mListener: VersionSelectionListener?) : RecyclerView.Adapter<VersionSelectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersionSelectionViewHolder {
        return VersionSelectionViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_version, parent, false))
    }

    override fun onBindViewHolder(holder: VersionSelectionViewHolder, position: Int) {
        val contentView = holder.contentView ?: return

        holder.item = mValues[position]
        contentView.text = mValues[position].displayText

        val currentIsSelected = CurrentSelected.getVersion() != null && !TextUtils.isEmpty(CurrentSelected.getVersion()) && CurrentSelected.getVersion() == mValues[position].abbreviation

        contentView.setTextColor(ContextCompat.getColor(contentView.context, if (currentIsSelected) R.color.primary else R.color.primary_text))
        contentView.setTypeface(null, if (currentIsSelected) Typeface.BOLD else Typeface.NORMAL)

        holder.itemView.setOnClickListener { v ->
            if (mListener != null && holder.item != null) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onVersionSelected(holder.item!!.abbreviation)
            }
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }
}
