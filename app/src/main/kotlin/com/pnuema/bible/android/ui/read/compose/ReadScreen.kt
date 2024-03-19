package com.pnuema.bible.android.ui.read.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.read.state.CopyrightViewState
import com.pnuema.bible.android.ui.read.state.ReadUiState
import com.pnuema.bible.android.ui.read.state.VerseViewState
import com.pnuema.bible.android.ui.read.state.VersionUiState
import com.pnuema.bible.android.ui.read.state.VersionViewState
import kotlinx.coroutines.launch

@Composable
fun ReadScreen(
    book: String,
    chapter: String,
    verseToFocus: Int,
    version: VersionUiState.Version,
    verses: ReadUiState.Verses,
    onBookChapterClicked: () -> Unit,
    onVersionClicked: () -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    BibleTheme {
        Scaffold(
            topBar = {
                BibleAppBar(
                    book = book,
                    chapter = chapter,
                    versionAbbreviation = version.version.abbreviation,
                    onBookChapterClicked = onBookChapterClicked,
                    onVersionClicked = onVersionClicked
                )
            }
        ) { paddingValues ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .fillMaxSize(),
                content = {
                    itemsIndexed(verses.verses) { index, item ->
                        VerseItem(state = item)

                        if (index == verses.verses.lastIndex) {
                            if (version.version.copyright.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(16.dp))
                                CopyrightItem(state = CopyrightViewState(version.version.copyright))
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    coroutineScope.launch {
                        if (listState.isScrollInProgress) return@launch
                        listState.animateScrollToItem(verses.verses.indexOfFirst { it.verseNumber == verseToFocus })
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun ReadScreen_Preview() {
    BibleTheme {
        ReadScreen(
            book = "Genesis",
            chapter = "1",
            verseToFocus = 1,
            version = VersionUiState.Version(version = VersionViewState("ERV", "erv", "")),
            verses = ReadUiState.Verses(
                verses = listOf(
                    VerseViewState(1, "In the beginning god created the heavens and the earth."),
                    VerseViewState(2, "In the beginning god created the heavens and the earth."),
                    VerseViewState(3, "In the beginning god created the heavens and the earth."),
                    VerseViewState(4, "In the beginning god created the heavens and the earth."),
                    VerseViewState(5, "In the beginning god created the heavens and the earth.")
                )
            ),
            onBookChapterClicked = {},
            onVersionClicked = {},
        )
    }
}