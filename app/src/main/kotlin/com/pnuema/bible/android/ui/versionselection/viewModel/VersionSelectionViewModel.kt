package com.pnuema.bible.android.ui.versionselection.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.data.firefly.VersionsDomain
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.ui.versionselection.state.VersionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VersionSelectionViewModel @Inject constructor(
    private val fireflyRepository: FireflyRepository
): ViewModel() {
    private val _state: MutableStateFlow<VersionsState> = MutableStateFlow(VersionsState())
    val state: StateFlow<VersionsState> get() = _state.asStateFlow()

    fun showDownloadVersionDialog(version: IVersion) {
        _state.update {
            it.copy(downloadDialogVersion = version)
        }
    }

    fun showRemoveVersionDialog(version: IVersion) {
        _state.update {
            it.copy(removeDialogVersion = version)
        }
    }

    fun clearDialogs() {
        _state.update {
            it.copy(
                downloadDialogVersion = null,
                removeDialogVersion = null,
            )
        }
    }

    fun loadVersions() {
        _state.update {
            it.copy(versions = VersionsDomain())
        }
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getVersions().collect { versions ->
                _state.update {
                    it.copy(versions = versions)
                }
            }
        }
    }

    fun removeOfflineVersion(versionAbbr: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.removeOfflineVersion(version = versionAbbr)
            loadVersions()
        }
    }
}