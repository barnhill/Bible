package com.pnuema.bible.android.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.dialogs.VersionSelectionListener
import com.pnuema.bible.android.ui.viewholders.VersionSelectionViewHolder

/**
 * [RecyclerView.Adapter] that can display a [IVersion] and makes a call to the
 * specified [BCVSelectionListener].
 */
class VersionSelectionRecyclerViewAdapter(private var versions: List<IVersion>, private val listener: VersionSelectionListener?) : RecyclerView.Adapter<VersionSelectionViewHolder>() {

    fun setVersions(versions: List<IVersion>) {
        this.versions = versions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersionSelectionViewHolder {
        return VersionSelectionViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: VersionSelectionViewHolder, position: Int) {
        val version = versions[position]
        holder.bind(version)
    }

    override fun getItemCount(): Int {
        return versions.size
    }
}
