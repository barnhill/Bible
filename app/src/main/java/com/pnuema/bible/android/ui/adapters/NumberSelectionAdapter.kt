package com.pnuema.bible.android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.ui.dialogs.NumberSelectionListener
import com.pnuema.bible.android.ui.viewholders.NumberSelectionViewHolder

class NumberSelectionAdapter(private val mMaxNumber: Int, private val mCurrentSelected: Int?, private val mOnClickListener: NumberSelectionListener) : RecyclerView.Adapter<NumberSelectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberSelectionViewHolder {
        return NumberSelectionViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_number, parent, false))
    }

    override fun onBindViewHolder(holder: NumberSelectionViewHolder, position: Int) {
        val pos = position + 1
        holder.bind(pos, mCurrentSelected != null && mCurrentSelected == pos, mOnClickListener)
    }

    override fun getItemCount(): Int {
        return mMaxNumber
    }
}
