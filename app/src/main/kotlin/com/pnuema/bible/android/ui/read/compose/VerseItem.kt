package com.pnuema.bible.android.ui.read.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.read.state.VerseViewState

@Composable
fun VerseItem(
    state: VerseViewState
) {
    val annotatedVerse = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary, fontSize = MaterialTheme.typography.titleSmall.fontSize)) {
            append("    ")
            append(state.verseNumber.toString())
            append("  ")
        }
        append(state.verseText.trimMargin("¶").trim())
    }
    Text(
        text = annotatedVerse,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Preview(showBackground = true)
@Composable
private fun VerseItem_Preview() {
    BibleTheme {
        VerseItem(state = VerseViewState(1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."))
    }
}