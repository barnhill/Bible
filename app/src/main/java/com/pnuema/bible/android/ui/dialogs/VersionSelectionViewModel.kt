package com.pnuema.bible.android.ui.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pnuema.bible.android.data.firefly.Versions
import com.pnuema.bible.android.retrievers.FireflyRetriever

class VersionSelectionViewModel: ViewModel() {
    val versions: LiveData<Versions> = MutableLiveData<Versions>()

    fun loadVersions() {
        FireflyRetriever.get().getVersions().observeForever { versions ->
            (this.versions as MutableLiveData<Versions>).value = versions
        }
    }
}