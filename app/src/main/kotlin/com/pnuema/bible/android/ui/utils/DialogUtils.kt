package com.pnuema.bible.android.ui.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.pnuema.bible.android.R

import com.pnuema.bible.android.ui.dialogs.BCVDialog
import com.pnuema.bible.android.ui.dialogs.NotifySelectionCompleted
import com.pnuema.bible.android.ui.dialogs.NotifyVersionSelectionCompleted
import com.pnuema.bible.android.ui.dialogs.VersionSelectionDialog

/**
 * Dialog utilities to generate and display dialogs.
 */
object DialogUtils {

    fun showBookChapterVersePicker(activity: FragmentActivity, bcv: BCVDialog.BCV, listener: NotifySelectionCompleted) {
        closeDialogs<VersionSelectionDialog>(activity.supportFragmentManager)
        activity.supportFragmentManager.commit {
            setCustomAnimations(R.anim.slide_in_bottom, 0, 0, 0)
            addToBackStack(BCVDialog::class.java.simpleName)
            replace(R.id.read_fragment, BCVDialog.instantiate(bcv, listener), BCVDialog::class.java.simpleName)
        }
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
