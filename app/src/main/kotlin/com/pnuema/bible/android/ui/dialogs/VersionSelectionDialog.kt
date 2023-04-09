package com.pnuema.bible.android.ui.dialogs

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.databinding.DialogVersionPickerBinding
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.LanguageUtils
import com.pnuema.bible.android.ui.adapters.VersionSelectionRecyclerViewAdapter

class VersionSelectionDialog() : DialogFragment(), VersionSelectionListener {
    private lateinit var versionSelectionCompleted: NotifyVersionSelectionCompleted
    private val viewModel: VersionSelectionViewModel by viewModels()
    private val versions = ArrayList<IVersion>()
    private var _binding: DialogVersionPickerBinding? = null
    private val binding: DialogVersionPickerBinding get() = _binding!!

    private val adapter: VersionSelectionRecyclerViewAdapter get() = binding.versionRecyclerView.adapter as VersionSelectionRecyclerViewAdapter

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogVersionPickerBinding.inflate(layoutInflater)

        with(binding.versionRecyclerView) {
            adapter = VersionSelectionRecyclerViewAdapter(this@VersionSelectionDialog)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

        viewModel.versions.observe(viewLifecycleOwner) {
            versions.clear()
            val lang = LanguageUtils.iSOLanguage

            versions.addAll(it.versions.filter { iVer -> iVer.language.contains(lang) })

            adapter.submitList(versions)
        }

        viewModel.loadVersions()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}