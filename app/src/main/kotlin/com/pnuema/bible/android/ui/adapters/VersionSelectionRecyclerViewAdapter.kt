package com.pnuema.bible.android.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.ui.dialogs.BCVSelectionListener
import com.pnuema.bible.android.ui.dialogs.VersionSelectionListener
import com.pnuema.bible.android.ui.viewholders.VersionSelectionViewHolder

/**
 * [RecyclerView.Adapter] that can display a [IVersion] and makes a call to the
 * specified [BCVSelectionListener].
 */
class VersionSelectionRecyclerViewAdapter(
    private val listener: VersionSelectionListener,
) : ListAdapter<IVersion, VersionSelectionViewHolder>(
    object : ItemCallback<IVersion>() {
        override fun areItemsTheSame(oldItem: IVersion, newItem: IVersion): Boolean = oldItem.javaClass == newItem.javaClass
        override fun areContentsTheSame(oldItem: IVersion, newItem: IVersion): Boolean = oldItem.convertToOfflineModel() == newItem.convertToOfflineModel()
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VersionSelectionViewHolder = VersionSelectionViewHolder(parent, listener)
    override fun onBindViewHolder(holder: VersionSelectionViewHolder, position: Int) = holder.bind(currentList[position])
}
