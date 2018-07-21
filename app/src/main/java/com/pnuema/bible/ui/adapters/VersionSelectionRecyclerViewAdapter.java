package com.pnuema.bible.ui.adapters;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.bible.R;
import com.pnuema.bible.data.IVersion;
import com.pnuema.bible.statics.CurrentSelected;
import com.pnuema.bible.ui.dialogs.BCVSelectionListener;
import com.pnuema.bible.ui.dialogs.VersionSelectionListener;
import com.pnuema.bible.ui.viewholders.VersionSelectionViewHolder;

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

    @NonNull
    @Override
    public VersionSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VersionSelectionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_version, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final VersionSelectionViewHolder holder, int position) {
        TextView contentView = holder.getContentView();
        if (contentView == null) {
            return;
        }

        holder.setItem(mValues.get(position));
        contentView.setText(mValues.get(position).getDisplayText());

        boolean currentIsSelected = CurrentSelected.getVersion() != null && CurrentSelected.getVersion().getId() != null && CurrentSelected.getVersion().getId().equals(mValues.get(position).getId());

        contentView.setTextColor(ContextCompat.getColor(contentView.getContext(), currentIsSelected ? R.color.primary : R.color.primary_text));
        contentView.setTypeface(null, currentIsSelected ? Typeface.BOLD : Typeface.NORMAL);

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onVersionSelected(holder.getItem());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
