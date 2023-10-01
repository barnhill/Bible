package com.pnuema.bible.android.ui.dialogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.firefly.VersionsDomain
import com.pnuema.bible.android.repository.FireflyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VersionSelectionViewModel @Inject constructor(private val fireflyRepository: FireflyRepository): ViewModel() {
    private val _versions: MutableSharedFlow<VersionsDomain> = MutableSharedFlow()
    val versions: SharedFlow<VersionsDomain> = _versions

    fun loadVersions() {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getVersions().collect { versions ->
                _versions.emit(versions)
            }
        }
    }
}