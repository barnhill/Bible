package com.pnuema.bible.android.ui.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.firefly.Versions
import com.pnuema.bible.android.repository.FireflyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VersionSelectionViewModel(private val fireflyRepository: FireflyRepository = FireflyRepository()): ViewModel() {
    val versions: LiveData<Versions> = MutableLiveData()

    fun loadVersions() {
        viewModelScope.launch(Dispatchers.IO) {
            (versions as MutableLiveData<Versions>).postValue(fireflyRepository.getVersions())
        }
    }
}