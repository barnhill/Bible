package com.pnuema.bible.android.ui.versionselection.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pnuema.bible.android.ui.utils.setContent
import com.pnuema.bible.android.ui.versionselection.dialogs.compose.DownloadVersionScreen
import com.pnuema.bible.android.ui.versionselection.ui.VersionSelectionDialog
import com.pnuema.bible.android.ui.versionselection.viewModel.DownloadVersionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadVersionDialog : DialogFragment() {
    companion object {
        const val VERSION_TO_DOWNLOAD = "VERSION_TO_DOWNLOAD"

        fun newInstance(
            version: String,
            callback: VersionSelectionDialog.DownloadCompleted
        ): DownloadVersionDialog {
            return DownloadVersionDialog().apply {
                isCancelable = false
                this.callback = callback
                arguments = bundleOf(
                    VERSION_TO_DOWNLOAD to version
                )
            }
        }
    }

    var callback: VersionSelectionDialog.DownloadCompleted? = null
    private val viewModel: DownloadVersionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val versionToDownload = requireArguments().getString(VERSION_TO_DOWNLOAD) ?: run {
            dismiss()
            ""
        }

        viewModel.downloadVersion(versionToDownload)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = setContent {
        val state by viewModel.progress.collectAsStateWithLifecycle()

        LaunchedEffect(key1 = state.isComplete, block = {
            if (state.isComplete) {
                callback?.onDownloadComplete()
                dismiss()
            }
        })

        DownloadVersionScreen(
            state = state
        )
    }
}