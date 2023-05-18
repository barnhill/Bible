package com.pnuema.bible.android.ui.dialogs.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.repository.FireflyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

class DownloadVersionViewModel constructor(
    private val fireflyRepository: FireflyRepository = FireflyRepository()
): ViewModel() {

    private val _progressTotal: MutableSharedFlow<Int> = MutableSharedFlow()
    val progressTotal = _progressTotal.asSharedFlow()

    private val _progress: MutableSharedFlow<Unit> = MutableSharedFlow()
    val progress = _progress.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun downloadVersion(version: String, lifecycle: Lifecycle) {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getBooks(version)
                .flowWithLifecycle(lifecycle)
                .flowOn(Dispatchers.IO)
                .collect { booksDomain ->

                    _progressTotal.emit(booksDomain.books.count())

                    //limit concurrency
                    val requestSemaphore = Semaphore(5)

                    booksDomain.books.sortedBy { it.getId() }.map { book ->
                        requestSemaphore.withPermit {
                            fireflyRepository.getVersesByBook(version, book.getId())
                            _progress.emit(Unit)
                        }
                    }
            }
        }
    }
}