package com.pnuema.bible.android.ui.versionselection.dialogs

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.DialogDownloadVersionBinding
import com.pnuema.bible.android.ui.versionselection.viewModel.DownloadVersionViewModel
import com.pnuema.bible.android.ui.utils.viewBinding
import com.pnuema.bible.android.ui.versionselection.ui.VersionSelectionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DownloadVersionDialog : DialogFragment(R.layout.dialog_download_version) {
    companion object {
        const val VERSION_TO_DOWNLOAD = "VERSION_TO_DOWNLOAD"

        fun newInstance(version: String, callback: VersionSelectionDialog.DownloadCompleted): DownloadVersionDialog {
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
    private val binding: DialogDownloadVersionBinding by viewBinding()
    private val viewModel: DownloadVersionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val versionToDownload = requireArguments().getString(VERSION_TO_DOWNLOAD) ?: run {
            dismiss()
            return
        }

        binding.downloadDialogTitle.text = getString(R.string.downloading_version_x, versionToDownload.uppercase())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.progress
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .flowOn(Dispatchers.Main)
                .collect { state ->
                    when(state) {
                        is DownloadProgress.ProgressByOne -> binding.downloadDialogProgress.incrementProgressBy(1)
                        is DownloadProgress.Max -> binding.downloadDialogProgress.max = state.max
                        is DownloadProgress.Complete -> {
                            callback?.onDownloadComplete()
                            dismiss()
                        }
                    }
                }
        }

        viewModel.downloadVersion(versionToDownload, viewLifecycleOwner.lifecycle)
    }
}