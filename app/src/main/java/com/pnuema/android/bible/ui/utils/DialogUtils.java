package com.pnuema.android.bible.ui.utils;

import androidx.fragment.app.FragmentActivity;

import com.pnuema.android.bible.ui.dialogs.BCVDialog;
import com.pnuema.android.bible.ui.dialogs.NotifySelectionCompleted;
import com.pnuema.android.bible.ui.dialogs.NotifyVersionSelectionCompleted;
import com.pnuema.android.bible.ui.dialogs.VersionSelectionDialog;

/**
 * Dialog utilities to generate and display dialogs.
 */
public final class DialogUtils {
    private DialogUtils() {
    }

    public static void showBookChapterVersePicker(final FragmentActivity activity, final BCVDialog.BCV bcv, final NotifySelectionCompleted listener) {
        BCVDialog.Companion.instantiate(bcv, listener).show(activity.getSupportFragmentManager(), BCVDialog.class.getSimpleName());
    }

    public static void showVersionsPicker(final FragmentActivity activity, final NotifyVersionSelectionCompleted listener) {
        VersionSelectionDialog.Companion.instantiate(listener).show(activity.getSupportFragmentManager(), VersionSelectionDialog.class.getSimpleName());
    }
}
