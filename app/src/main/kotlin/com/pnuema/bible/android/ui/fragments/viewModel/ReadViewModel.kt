package com.pnuema.bible.android.ui.fragments.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.fragments.uiStates.ReadBookUiState
import com.pnuema.bible.android.ui.fragments.uiStates.ReadUiState
import com.pnuema.bible.android.ui.fragments.uiStates.VersionUiState
import com.pnuema.bible.android.ui.utils.Extensions.toViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReadViewModel constructor(private val fireflyRepository: FireflyRepository = FireflyRepository()): ViewModel() {
    private val _stateVersions: MutableStateFlow<VersionUiState> = MutableStateFlow(VersionUiState.Idle)
    val stateVersions: StateFlow<VersionUiState> get() = _stateVersions

    private val _stateVerses: MutableStateFlow<ReadUiState> = MutableStateFlow(ReadUiState.Idle)
    val stateVerses: StateFlow<ReadUiState> get() = _stateVerses

    private val _stateBook: MutableStateFlow<ReadBookUiState> = MutableStateFlow(ReadBookUiState.Idle)
    val stateBook: StateFlow<ReadBookUiState> get() = _stateBook

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getVerses(CurrentSelected.version, CurrentSelected.book, CurrentSelected.chapter).collect { verses ->
                _stateVerses.value = ReadUiState.Verses(verses.verses.map { it.toViewState() })
            }
            fireflyRepository.getVersions().collect { versions ->
                _stateVersions.value = VersionUiState.Versions(versions.versions.map { it.toViewState() })
            }
            fireflyRepository.getBooks().collect { books ->
                _stateBook.value = ReadBookUiState.Books(books.books.map { it.toViewState() })
            }
        }
    }

    suspend fun search(query: String) {
        /*FireflyRetriever.get().searchVerses(query = query).verses.forEach {
            Log.w("Brad", "" + it)
        }*/
    }
}