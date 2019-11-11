package com.pnuema.android.bible.ui.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

import com.pnuema.android.bible.ui.dialogs.BCVDialog
import com.pnuema.android.bible.ui.dialogs.NotifySelectionCompleted
import com.pnuema.android.bible.ui.dialogs.NotifyVersionSelectionCompleted
import com.pnuema.android.bible.ui.dialogs.VersionSelectionDialog

/**
 * Dialog utilities to generate and display dialogs.
 */
object DialogUtils {

    fun showBookChapterVersePicker(activity: FragmentActivity, bcv: BCVDialog.BCV, listener: NotifySelectionCompleted) {
        closeDialogs<VersionSelectionDialog>(activity.supportFragmentManager)
        BCVDialog.instantiate(bcv, listener).show(activity.supportFragmentManager, BCVDialog::class.java.simpleName)
    }

    fun showVersionsPicker(activity: FragmentActivity, listener: NotifyVersionSelectionCompleted) {
        closeDialogs<VersionSelectionDialog>(activity.supportFragmentManager)
        VersionSelectionDialog.instantiate(listener).show(activity.supportFragmentManager, VersionSelectionDialog::class.java.simpleName)
    }

    private inline fun <reified T : DialogFragment>closeDialogs(supportFragmentManager: FragmentManager) {
        supportFragmentManager.fragments.filterIsInstance<T>().forEach {
            it.dismiss()
        }
    }
}
