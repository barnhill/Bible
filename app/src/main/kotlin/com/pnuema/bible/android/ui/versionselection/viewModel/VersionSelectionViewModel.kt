package com.pnuema.bible.android.ui.versionselection.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.data.IVersion
import com.pnuema.bible.android.data.firefly.VersionsDomain
import com.pnuema.bible.android.database.VersionOfflineDao
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.ui.versionselection.state.DownloadProgress
import com.pnuema.bible.android.ui.versionselection.state.VersionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import javax.inject.Inject

@HiltViewModel
class VersionSelectionViewModel @Inject constructor(
    private val fireflyRepository: FireflyRepository,
    private val offlineDao: VersionOfflineDao,
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

    fun downloadVersion(versionToDownload: String) {
        viewModelScope.launch {
            fireflyRepository.getVersions()
                .flowOn(Dispatchers.IO)
                .collect { versions ->
                    val display = versions.versions.find { it.abbreviation == versionToDownload }?.getDisplayText() ?: ""
                    _state.update {
                        it.copy(
                            downloadProgress = it.downloadProgress?.copy(
                                versionToDownloadDisplay = display
                            ),
                        )
                    }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getBooks(versionToDownload)
                .flowOn(Dispatchers.IO)
                .collect { booksDomain ->

                    val total = booksDomain.books.count()
                    var progress = 0

                    _state.update {
                        it.copy(
                            downloadProgress = it.downloadProgress?.copy(max = total) ?: DownloadProgress(
                                versionToDownload = versionToDownload,
                                versionToDownloadDisplay = it.downloadDialogVersion?.getDisplayText() ?: "",
                                max = total,
                            )
                        )
                    }

                    //limit concurrency
                    val requestSemaphore = Semaphore(5)

                    booksDomain.books.sortedBy { it.getId() }.parallelStream().forEach { book ->
                        viewModelScope.launch(Dispatchers.IO) {
                            requestSemaphore.withPermit {
                                fireflyRepository.getVersesByBook(versionToDownload, book.getId())

                                _state.update {
                                    it.copy(
                                        downloadProgress = it.downloadProgress?.copy(progress = it.downloadProgress.progress + 1) ?: DownloadProgress(
                                            versionToDownload = versionToDownload,
                                            versionToDownloadDisplay = it.downloadDialogVersion?.getDisplayText() ?: "",
                                            max = total,
                                        )
                                    )
                                }

                                if (total == ++progress) {
                                    offlineDao.markCompleteOfflineAvailable(version = versionToDownload, isAvailableOffline = true)
                                    _state.update {
                                        it.copy(
                                            downloadProgress = null
                                        )
                                    }
                                    loadVersions()
                                }
                            }
                        }
                    }
                }
        }
    }
}