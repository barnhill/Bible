package com.pnuema.bible.android.ui.versionselection.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.getValue
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.utils.setContent
import com.pnuema.bible.android.ui.versionselection.VersionSelectionListener
import com.pnuema.bible.android.ui.versionselection.ui.compose.VersionSelectionScreen
import com.pnuema.bible.android.ui.versionselection.viewModel.VersionSelectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VersionSelectionDialog : Fragment(), VersionSelectionListener {
    companion object {
        const val RESULT_KEY = "VersionSelectionDialog_RESULT_KEY"
    }
    private val viewModel: VersionSelectionViewModel by viewModels()

    private val backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().supportFragmentManager.popBackStack()
            remove()
        }
    }

    override fun onVersionSelected(version: String) {
        setFragmentResult(RESULT_KEY, bundleOf(RESULT_KEY to version))
        requireActivity().onBackPressedDispatcher.onBackPressed()
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
                    viewModel.downloadVersion(it.abbreviation)
                },
                onRemoveApproved = {
                    viewModel.removeOfflineVersion(it.abbreviation)
                },
                onBackPressed = {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
            )
        }

        viewModel.loadVersions()
    }.apply {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)
    }
}