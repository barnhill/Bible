package com.pnuema.simplebible.ui.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.pnuema.simplebible.R;
import com.pnuema.simplebible.ui.fragments.BCVDialog;
import com.pnuema.simplebible.ui.fragments.NotifySelectionCompleted;

import java.util.ArrayList;
import java.util.List;

/**
 * Dialog utilities to generate and display dialogs.
 */
public final class DialogUtils {
    /**
     * Show number picker grid dialog.
     * @param context {@link Context} to attach the dialog to.
     * @param title Title of the dialog to display
     * @param max Maximum value to display 1-max on the screen
     * @param onItemClickListener When the number is clicked it will trigger this {@link AdapterView.OnItemClickListener}
     */
    public static AlertDialog showNumberPickerDialog(Context context, @StringRes int title, int max, AdapterView.OnItemClickListener onItemClickListener) {
        GridView gridView = new GridView(context);

        List<Integer> mList = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            mList.add(i);
        }

        gridView.setAdapter(new ArrayAdapter<>(context, R.layout.item_number, mList));
        gridView.setNumColumns(4);
        gridView.setOnItemClickListener(onItemClickListener);

        // Set grid view to alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(gridView);
        builder.setTitle(title);
        builder.setCancelable(true);
        return builder.show();
    }

    public static void showBookChapterVersePicker(FragmentActivity activity, BCVDialog.BCV bcv, NotifySelectionCompleted listener) {
        BCVDialog.instantiate(bcv, listener).show(activity.getSupportFragmentManager(), "BCVDialog");
    }
}
