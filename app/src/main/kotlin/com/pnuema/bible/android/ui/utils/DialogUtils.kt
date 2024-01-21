package com.pnuema.bible.android.ui.utils

import androidx.fragment.app.FragmentManager
import com.pnuema.bible.android.ui.bookchapterverse.BCVDialog
import com.pnuema.bible.android.ui.bookchapterverse.NotifySelectionCompleted

/**
 * Dialog utilities to generate and display dialogs.
 */
object DialogUtils {

    fun showBookChapterVersePicker(fragmentManager: FragmentManager, bcv: BCVDialog.BCV, listener: NotifySelectionCompleted) {
        /*fragmentManager.commit {
            setCustomAnimations(androidx.appcompat.R.anim.abc_slide_in_top, 0, 0, 0)
            addToBackStack(BCVDialog::class.java.simpleName)
            replace(R.id.read_fragment, BCVDialog.instantiate(bcv, listener), BCVDialog::class.java.simpleName)
        }*/
    }
}