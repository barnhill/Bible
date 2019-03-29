package com.pnuema.android.bible.ui.adapters;

import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pnuema.android.bible.R;
import com.pnuema.android.bible.data.IVersion;
import com.pnuema.android.bible.statics.CurrentSelected;
import com.pnuema.android.bible.ui.dialogs.BCVSelectionListener;
import com.pnuema.android.bible.ui.dialogs.VersionSelectionListener;
import com.pnuema.android.bible.ui.viewholders.VersionSelectionViewHolder;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IVersion} and makes a call to the
 * specified {@link BCVSelectionListener}.
 */
public class VersionSelectionRecyclerViewAdapter extends RecyclerView.Adapter<VersionSelectionViewHolder> {
    private final List<IVersion> mValues;
    private final VersionSelectionListener mListener;

    public VersionSelectionRecyclerViewAdapter(final List<IVersion> items, final VersionSelectionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public VersionSelectionViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return new VersionSelectionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_version, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final VersionSelectionViewHolder holder, final int position) {
        final TextView contentView = holder.getContentView();
        if (contentView == null) {
            return;
        }

        holder.setItem(mValues.get(position));
        contentView.setText(mValues.get(position).getDisplayText());

        final boolean currentIsSelected = CurrentSelected.getVersion() != null && !TextUtils.isEmpty(CurrentSelected.getVersion()) && CurrentSelected.getVersion().equals(mValues.get(position).getAbbreviation());

        contentView.setTextColor(ContextCompat.getColor(contentView.getContext(), currentIsSelected ? R.color.primary : R.color.primary_text));
        contentView.setTypeface(null, currentIsSelected ? Typeface.BOLD : Typeface.NORMAL);

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null && holder.getItem() != null) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onVersionSelected(holder.getItem().getAbbreviation());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
