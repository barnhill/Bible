package com.pnuema.bible.android.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.DialogDownloadVersionBinding
import com.pnuema.bible.android.statics.CurrentSelected.version
import com.pnuema.bible.android.ui.dialogs.viewmodel.DownloadVersionViewModel
import com.pnuema.bible.android.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DownloadVersionDialog : DialogFragment(R.layout.dialog_download_version) {
    companion object {
        const val VERSION_TO_DOWNLOAD = "VERSION_TO_DOWNLOAD"
        fun newInstance(version: String): DownloadVersionDialog {
            return DownloadVersionDialog().apply {
                isCancelable = false
                arguments = Bundle().apply {
                    putString(VERSION_TO_DOWNLOAD, version)
                }
            }
        }
    }

    private val binding: DialogDownloadVersionBinding by viewBinding()
    private val viewModel: DownloadVersionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.downloadDialogTitle.text = getString(R.string.downloading_version_x, version)
        binding.downloadDialogProgress.progress = 0

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.progressTotal
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    binding.downloadDialogProgress.max = it
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.progress
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    binding.downloadDialogProgress.incrementProgressBy(1)

                    if (binding.downloadDialogProgress.progress == binding.downloadDialogProgress.max) {
                        dismiss()
                    }
                }
        }

        viewModel.downloadVersion(requireArguments().getString(VERSION_TO_DOWNLOAD) ?: "", viewLifecycleOwner.lifecycle)
    }
}