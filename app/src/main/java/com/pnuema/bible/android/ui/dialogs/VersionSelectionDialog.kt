package com.pnuema.bible.android.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.bible.android.R
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.statics.LanguageUtils
import com.pnuema.bible.android.ui.adapters.VersionSelectionRecyclerViewAdapter
import java.util.*

class VersionSelectionDialog() : DialogFragment(), VersionSelectionListener {
    private lateinit var viewModel: VersionSelectionViewModel
    private lateinit var adapter: VersionSelectionRecyclerViewAdapter
    private lateinit var versionSelectionCompleted: NotifyVersionSelectionCompleted
    private val versions = ArrayList<IVersion>()

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
        dismiss()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.dialog_version_picker, container)

        adapter = VersionSelectionRecyclerViewAdapter(versions, this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.versionRecyclerView)
        recyclerView.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(VersionSelectionViewModel::class.java)
        viewModel.versions.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            versions.clear()
            val lang = LanguageUtils.getISOLanguage()
            for (version in it.versions) {
                if (version.language.contains(lang)) {
                    versions.add(version)
                }
            }

            adapter.setVersions(versions)
        })

        viewModel.loadVersions()

        return view
    }
}