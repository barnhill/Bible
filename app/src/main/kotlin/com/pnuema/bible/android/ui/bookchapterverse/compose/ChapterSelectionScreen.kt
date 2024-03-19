package com.pnuema.bible.android.ui.bookchapterverse.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.bookchapterverse.state.ChapterCountViewState
import com.pnuema.bible.android.ui.bookchapterverse.state.ChaptersUiState

@Composable
fun ChapterSelectionScreen(
    chapters: ChaptersUiState,
    onClicked: (Int) -> Unit,
) {
    BibleTheme {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3),
            content = {
                when(chapters) {
                    is ChaptersUiState.ChaptersState -> {
                        items((1..chapters.viewState.chapterCount).toList()) {
                            ChapterItem(chapterNumber = it) {
                                onClicked(it)
                            }
                        }
                    }
                    is ChaptersUiState.Idle -> Unit
                    is ChaptersUiState.Loading -> Unit
                    is ChaptersUiState.NotLoading -> Unit
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChapterSelectionScreen_Preview() {
    BibleTheme {
        ChapterSelectionScreen(
            chapters = ChaptersUiState.ChaptersState(viewState = ChapterCountViewState(45)),
            onClicked = {}
        )
    }
}