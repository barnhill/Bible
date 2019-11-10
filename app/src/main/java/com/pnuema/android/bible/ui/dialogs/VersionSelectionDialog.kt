package com.pnuema.android.bible.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.pnuema.android.bible.R
import com.pnuema.android.bible.data.IVersion
import com.pnuema.android.bible.data.firefly.Versions
import com.pnuema.android.bible.retrievers.FireflyRetriever
import com.pnuema.android.bible.statics.CurrentSelected
import com.pnuema.android.bible.statics.LanguageUtils
import com.pnuema.android.bible.ui.adapters.VersionSelectionRecyclerViewAdapter
import java.util.*

class VersionSelectionDialog(notifySelectionCompleted: NotifyVersionSelectionCompleted) : DialogFragment(), VersionSelectionListener {
    private var mListener: NotifyVersionSelectionCompleted = notifySelectionCompleted
    private lateinit var mAdapter: VersionSelectionRecyclerViewAdapter
    private val mVersions = ArrayList<IVersion>()
    private val mRetriever = FireflyRetriever()

    companion object {
        fun instantiate(notifySelectionCompleted: NotifyVersionSelectionCompleted): VersionSelectionDialog {
            return VersionSelectionDialog(notifySelectionCompleted)
        }
    }

    override fun onVersionSelected(version: String) {
        CurrentSelected.setVersion(version)
        mListener.onSelectionComplete(version)
        dismiss()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.dialog_version_picker, container)

        mAdapter = VersionSelectionRecyclerViewAdapter(mVersions, this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.versionRecyclerView)
        recyclerView.adapter = mAdapter

        return view
    }

    override fun onResume() {
        super.onResume()
        mRetriever.getVersions().observe(viewLifecycleOwner, androidx.lifecycle.Observer { versions ->
            mVersions.clear()
            if (versions is Versions) {
                val lang = LanguageUtils.getISOLanguage()
                for (version in versions.versions) {
                    if (version.language.contains(lang)) {
                        mVersions.add(version)
                    }
                }
                mAdapter.notifyDataSetChanged()
            }
        })
    }
}