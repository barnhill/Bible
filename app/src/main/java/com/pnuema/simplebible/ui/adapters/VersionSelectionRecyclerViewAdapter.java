package com.pnuema.simplebible.ui.adapters;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.data.IVersion;
import com.pnuema.simplebible.statics.CurrentSelected;
import com.pnuema.simplebible.ui.dialogs.BCVSelectionListener;
import com.pnuema.simplebible.ui.dialogs.VersionSelectionListener;
import com.pnuema.simplebible.ui.viewholders.VersionSelectionViewHolder;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IVersion} and makes a call to the
 * specified {@link BCVSelectionListener}.
 */
public class VersionSelectionRecyclerViewAdapter extends RecyclerView.Adapter<VersionSelectionViewHolder> {
    private final List<IVersion> mValues;
    private final VersionSelectionListener mListener;

    public VersionSelectionRecyclerViewAdapter(List<IVersion> items, VersionSelectionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public VersionSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VersionSelectionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_version, parent, false));
    }

    @Override
    public void onBindViewHolder(final VersionSelectionViewHolder holder, int position) {
        TextView contentView = holder.getContentView();
        if (contentView == null) {
            return;
        }

        holder.setItem(mValues.get(position));
        contentView.setText(mValues.get(position).getDisplayText());

        boolean currentIsSelected = CurrentSelected.getVersion() != null && CurrentSelected.getVersion().getId().equals(mValues.get(position).getId());

        contentView.setTextColor(ContextCompat.getColor(contentView.getContext(), currentIsSelected ? R.color.primary : R.color.primary_text));
        contentView.setTypeface(null, currentIsSelected ? Typeface.BOLD : Typeface.NORMAL);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onVersionSelected(holder.getItem());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
