package com.pnuema.bible.android.ui.versionselection.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.getValue
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.utils.setContent
import com.pnuema.bible.android.ui.versionselection.NotifyVersionSelectionCompleted
import com.pnuema.bible.android.ui.versionselection.VersionSelectionListener
import com.pnuema.bible.android.ui.versionselection.dialogs.DownloadVersionDialog
import com.pnuema.bible.android.ui.versionselection.ui.compose.VersionSelectionScreen
import com.pnuema.bible.android.ui.versionselection.viewModel.VersionSelectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VersionSelectionDialog() : DialogFragment(), VersionSelectionListener {
    private lateinit var versionSelectionCompleted: NotifyVersionSelectionCompleted
    private val viewModel: VersionSelectionViewModel by viewModels()

    interface DownloadCompleted {
        fun onDownloadComplete()
    }

    constructor(notifySelectionCompleted: NotifyVersionSelectionCompleted): this() {
        versionSelectionCompleted = notifySelectionCompleted
    }

    companion object {
        fun instantiate(notifySelectionCompleted: NotifyVersionSelectionCompleted): VersionSelectionDialog {
            return VersionSelectionDialog(notifySelectionCompleted)
        }
    }

    override fun onVersionSelected(version: String) {
        CurrentSelected.version = version
        versionSelectionCompleted.onSelectionComplete(version)
        parentFragmentManager.popBackStackImmediate()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = setContent {
        val state by viewModel.state.collectAsStateWithLifecycle()

        BibleTheme {
            VersionSelectionScreen(
                state = state,
                onActionClicked = {
                    if (it.convertToOfflineModel().completeOffline)
                        viewModel.showRemoveVersionDialog(it)
                    else
                        viewModel.showDownloadVersionDialog(it)
                },
                onVersionClicked = {
                    onVersionSelected(it)
                },
                onDialogDismiss = { viewModel.clearDialogs() },
                onDownloadApproved = {
                    DownloadVersionDialog.newInstance(it.abbreviation, object : DownloadCompleted {
                        override fun onDownloadComplete() {
                            viewModel.loadVersions()
                        }
                    }).show(childFragmentManager, null)
                },
                onRemoveApproved = {
                    viewModel.removeOfflineVersion(it.abbreviation)
                }
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        viewModel.loadVersions()
    }
}