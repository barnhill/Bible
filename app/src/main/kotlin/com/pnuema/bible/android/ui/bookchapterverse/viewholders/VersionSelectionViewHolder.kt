package com.pnuema.bible.android.ui.bookchapterverse.viewholders

import android.graphics.Typeface
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.databinding.ListitemVersionBinding
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.dialogs.VersionSelectionListener

class VersionSelectionViewHolder(
        parent: ViewGroup,
        private val listener: VersionSelectionListener,
        private val binding: ListitemVersionBinding = ListitemVersionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) : RecyclerView.ViewHolder(binding.root) {
    override fun toString(): String {
        return super.toString() + " '" + (itemView as TextView).text + "'"
    }

    fun bind(version: IVersion) {
        binding.version.text = version.getDisplayText()

        val currentIsSelected = !TextUtils.isEmpty(CurrentSelected.version) && CurrentSelected.version == version.abbreviation

        binding.version.setTextColor(ContextCompat.getColor(binding.root.context, if (currentIsSelected) R.color.primary else R.color.primary_text))
        binding.version.setTypeface(null, if (currentIsSelected) Typeface.BOLD else Typeface.NORMAL)

        binding.version.setOnClickListener {
            listener.onVersionSelected(version.abbreviation)
        }

        binding.downloadIcon.apply {
            isVisible = !version.convertToOfflineModel().completeOffline
            setOnClickListener {
                listener.onVersionDownloadClicked(version.abbreviation)
            }
        }
    }
}