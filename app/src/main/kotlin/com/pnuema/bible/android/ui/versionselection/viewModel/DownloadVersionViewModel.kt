package com.pnuema.bible.android.ui.versionselection.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.database.VersionOfflineDao
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.ui.versionselection.dialogs.DownloadProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import javax.inject.Inject

@HiltViewModel
class DownloadVersionViewModel @Inject constructor(
    private val fireflyRepository: FireflyRepository,
    private val offlineDao: VersionOfflineDao,
): ViewModel() {

    private val _progress: MutableStateFlow<DownloadProgress> = MutableStateFlow(DownloadProgress())
    val progress = _progress.asStateFlow()

    fun downloadVersion(versionToDownload: String) {
        viewModelScope.launch {
            fireflyRepository.getVersions()
                .flowOn(Dispatchers.IO)
                .collect { versions ->
                    val display = versions.versions.find { it.abbreviation == versionToDownload }?.getDisplayText() ?: ""
                    _progress.update {
                        it.copy(versionToDownloadDisplay = display)
                    }
                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getBooks(versionToDownload)
                .flowOn(Dispatchers.IO)
                .collect { booksDomain ->

                    val total = booksDomain.books.count()
                    var progress = 0

                    _progress.update {
                        it.copy(max = total)
                    }

                    //limit concurrency
                    val requestSemaphore = Semaphore(5)

                    booksDomain.books.sortedBy { it.getId() }.parallelStream().forEach { book ->
                        viewModelScope.launch(Dispatchers.IO) {
                            requestSemaphore.withPermit {
                                fireflyRepository.getVersesByBook(versionToDownload, book.getId())

                                _progress.update {
                                    it.copy(progress = it.progress + 1)
                                }

                                if (total == ++progress) {
                                    offlineDao.markCompleteOfflineAvailable(version = versionToDownload, isAvailableOffline = true)
                                    _progress.update {
                                        it.copy(isComplete = true)
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }
}