package com.pnuema.bible.android.ui.dialogs.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.ui.dialogs.DownloadProgress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import javax.inject.Inject

@HiltViewModel
class DownloadVersionViewModel @Inject constructor(
    private val fireflyRepository: FireflyRepository
): ViewModel() {

    private val _progress: MutableSharedFlow<DownloadProgress> = MutableSharedFlow()
    val progress = _progress.asSharedFlow()

    fun downloadVersion(version: String, lifecycle: Lifecycle) {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getBooks(version)
                .flowWithLifecycle(lifecycle)
                .flowOn(Dispatchers.IO)
                .collect { booksDomain ->

                    val total = booksDomain.books.count()
                    var progress = 0

                    _progress.emit(DownloadProgress.Max(total))

                    //limit concurrency
                    val requestSemaphore = Semaphore(3)

                    booksDomain.books.sortedBy { it.getId() }.parallelStream().forEach { book ->
                        viewModelScope.launch(Dispatchers.IO) {
                            requestSemaphore.withPermit {
                                fireflyRepository.getVersesByBook(version, book.getId())
                                _progress.emit(DownloadProgress.ProgressByOne)

                                if (total == ++progress) {
                                    _progress.emit(DownloadProgress.Complete)
                                }
                            }
                        }
                    }
            }
        }
    }
}