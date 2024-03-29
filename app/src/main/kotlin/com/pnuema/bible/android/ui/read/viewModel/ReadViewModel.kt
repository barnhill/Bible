package com.pnuema.bible.android.ui.read.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pnuema.bible.android.repository.FireflyRepository
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.read.state.ReadBookUiState
import com.pnuema.bible.android.ui.read.state.ReadUiState
import com.pnuema.bible.android.ui.read.state.VersionUiState
import com.pnuema.bible.android.ui.utils.toViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadViewModel @Inject constructor(private val fireflyRepository: FireflyRepository): ViewModel() {
    private val _stateVersion: MutableStateFlow<VersionUiState> = MutableStateFlow(VersionUiState.Idle)
    val stateVersion = _stateVersion.asStateFlow()

    private val _stateVerses: MutableStateFlow<ReadUiState> = MutableStateFlow(ReadUiState.Idle)
    val stateVerses = _stateVerses.asStateFlow()

    private val _stateBook: MutableStateFlow<ReadBookUiState> = MutableStateFlow(ReadBookUiState.Idle)
    val stateBook = _stateBook.asStateFlow()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            fireflyRepository.getVersions().collect { versions ->
                _stateVersion.update {
                    VersionUiState.Version(versions.versions.first {
                        it.abbreviation.lowercase() == CurrentSelected.version.lowercase()
                    }.toViewState())
                }
            }
            fireflyRepository.getVerses(CurrentSelected.version, CurrentSelected.book, CurrentSelected.chapter).collect { verses ->
                _stateVerses.update { ReadUiState.Idle }
                _stateVerses.update { ReadUiState.Verses(verses = verses.verses.map { it.toViewState() }) }
            }
            fireflyRepository.getBooks(CurrentSelected.version).collect { books ->
                _stateBook.update { ReadBookUiState.Books(books.books.map { it.toViewState() }) }
            }
        }
    }

    suspend fun search(query: String) {
        /*FireflyRetriever.get().searchVerses(query = query).verses.forEach {
            Log.w("Brad", "" + it)
        }*/
    }
}