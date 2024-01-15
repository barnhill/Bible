package com.pnuema.bible.android.ui.versionselection.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.firefly.VersionsDomain
import com.pnuema.bible.android.repository.FireflyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VersionSelectionViewModel @Inject constructor(private val fireflyRepository: FireflyRepository): ViewModel() {
    private val _versions: MutableStateFlow<VersionsDomain> = MutableStateFlow(VersionsDomain())
    val versions: StateFlow<VersionsDomain> get() = _versions.asStateFlow()

    fun loadVersions() {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getVersions().collect { versions ->
                _versions.update {
                    it.copy(versions = versions.versions)
                }
            }
        }
    }
}