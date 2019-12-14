package com.pnuema.bible.android.ui.adapters

import android.graphics.Typeface
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.dialogs.VersionSelectionListener
import com.pnuema.bible.android.ui.viewholders.VersionSelectionViewHolder

/**
 * [RecyclerView.Adapter] that can display a [IVersion] and makes a call to the
 * specified [BCVSelectionListener].
 */
class VersionSelectionRecyclerViewAdapter(private var mValues: List<IVersion>, private val mListener: VersionSelectionListener?) : RecyclerView.Adapter<VersionSelectionViewHolder>() {

    fun setVersions(versions: List<IVersion>) {
        mValues = versions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersionSelectionViewHolder {
        return VersionSelectionViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_version, parent, false))
    }

    override fun onBindViewHolder(holder: VersionSelectionViewHolder, position: Int) {
        val contentView = holder.contentView ?: return

        holder.item = mValues[position]
        contentView.text = mValues[position].displayText

        val currentIsSelected = !TextUtils.isEmpty(CurrentSelected.version) && CurrentSelected.version == mValues[position].abbreviation

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
