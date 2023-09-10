package com.pnuema.bible.android.ui.dialogs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.pnuema.bible.android.R
import com.pnuema.bible.android.databinding.DialogDownloadVersionBinding
import com.pnuema.bible.android.ui.dialogs.viewmodel.DownloadVersionViewModel
import com.pnuema.bible.android.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
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
                        is DownloadProgress.Complete -> dismiss()
                    }
                }
        }

        viewModel.downloadVersion(versionToDownload, viewLifecycleOwner.lifecycle)
    }
}