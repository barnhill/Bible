package com.pnuema.bible.ui.utils;

import android.support.v4.app.FragmentActivity;

import com.pnuema.bible.ui.dialogs.BCVDialog;
import com.pnuema.bible.ui.dialogs.NotifySelectionCompleted;
import com.pnuema.bible.ui.dialogs.NotifyVersionSelectionCompleted;
import com.pnuema.bible.ui.dialogs.VersionSelectionDialog;

/**
 * Dialog utilities to generate and display dialogs.
 */
public final class DialogUtils {
    private DialogUtils() {
    }

    public static void showBookChapterVersePicker(FragmentActivity activity, BCVDialog.BCV bcv, NotifySelectionCompleted listener) {
        BCVDialog.instantiate(bcv, listener).show(activity.getSupportFragmentManager(), "BCVDialog");
    }

    public static void showVersionsPicker(FragmentActivity activity, NotifyVersionSelectionCompleted listener) {
        VersionSelectionDialog.instantiate(listener).show(activity.getSupportFragmentManager(), "VersionsDialog");
    }
}
