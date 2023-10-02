package com.pnuema.bible.android.ui.utils

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.pnuema.bible.android.R
import com.pnuema.bible.android.ui.bookchapterverse.BCVDialog
import com.pnuema.bible.android.ui.bookchapterverse.NotifySelectionCompleted
import com.pnuema.bible.android.ui.dialogs.NotifyVersionSelectionCompleted
import com.pnuema.bible.android.ui.dialogs.VersionSelectionDialog

/**
 * Dialog utilities to generate and display dialogs.
 */
object DialogUtils {

    fun showBookChapterVersePicker(fragmentManager: FragmentManager, bcv: BCVDialog.BCV, listener: NotifySelectionCompleted) {
        fragmentManager.commit {
            setCustomAnimations(androidx.appcompat.R.anim.abc_slide_in_top, 0, 0, 0)
            addToBackStack(BCVDialog::class.java.simpleName)
            replace(R.id.read_fragment, BCVDialog.instantiate(bcv, listener), BCVDialog::class.java.simpleName)
        }
    }

    fun showVersionsPicker(fragmentManager: FragmentManager, listener: NotifyVersionSelectionCompleted) {
        fragmentManager.commit {
            setCustomAnimations(androidx.appcompat.R.anim.abc_slide_in_top, 0, 0, 0)
            addToBackStack(VersionSelectionDialog::class.java.simpleName)
            replace(R.id.read_fragment, VersionSelectionDialog.instantiate(listener), VersionSelectionDialog::class.java.simpleName)
        }
    }
}
