package com.pnuema.bible.android.ui.bookchapterverse.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.bookchapterverse.state.VerseCountViewState
import com.pnuema.bible.android.ui.bookchapterverse.state.VersesUiState

@Composable
fun VerseSelectionScreen(
    verses: VersesUiState,
    onClicked: (Int) -> Unit,
) {
    BibleTheme {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3),
            content = {
                when(verses) {
                    is VersesUiState.Idle -> Unit
                    is VersesUiState.Loading -> Unit
                    is VersesUiState.NotLoading -> Unit
                    is VersesUiState.VersesState -> {
                        items((1..verses.viewState.verseCount).toList()) {
                            VerseItem(verseNumber = it) {
                                onClicked(it)
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VerseSelectionScreen_Preview() {
    BibleTheme {
        VerseSelectionScreen(
            verses = VersesUiState.VersesState(viewState = VerseCountViewState(15)),
            onClicked = {}
        )
    }
}