package com.pnuema.bible.android.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.databinding.DialogVersionPickerBinding
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.LanguageUtils
import com.pnuema.bible.android.ui.adapters.VersionSelectionRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VersionSelectionDialog() : DialogFragment(), VersionSelectionListener {
    private lateinit var versionSelectionCompleted: NotifyVersionSelectionCompleted
    private val viewModel: VersionSelectionViewModel by viewModels()
    private val versions = ArrayList<IVersion>()
    private var _binding: DialogVersionPickerBinding? = null
    private val binding: DialogVersionPickerBinding get() = _binding!!

    private val adapter: VersionSelectionRecyclerViewAdapter get() = binding.versionRecyclerView.adapter as VersionSelectionRecyclerViewAdapter

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

    override fun onVersionDownloadClicked(version: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.download_confirm_message, version.uppercase()))
            .setPositiveButton(R.string.download_confirm_yes) { _, _ ->
                DownloadVersionDialog.newInstance(version, object : DownloadCompleted {
                    override fun onDownloadComplete() {
                        viewModel.loadVersions()
                    }
                }).show(childFragmentManager, null)
            }
            .setNegativeButton(R.string.download_confirm_no) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogVersionPickerBinding.inflate(layoutInflater)

        with(binding.versionRecyclerView) {
            adapter = VersionSelectionRecyclerViewAdapter(this@VersionSelectionDialog)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.versions
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    adapter.submitList(it.versions.filter { iVer -> iVer.language.contains(LanguageUtils.iSOLanguage) })
                }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadVersions()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}