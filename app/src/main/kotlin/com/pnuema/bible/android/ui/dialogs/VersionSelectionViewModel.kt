package com.pnuema.bible.android.ui.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.firefly.Versions
import com.pnuema.bible.android.retrievers.FireflyRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VersionSelectionViewModel: ViewModel() {
    val versions: LiveData<Versions> = MutableLiveData()

    fun loadVersions() {
        viewModelScope.launch(Dispatchers.IO) {
            (versions as MutableLiveData<Versions>).postValue(FireflyRetriever.get().getVersions())
        }
    }
}