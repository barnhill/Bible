package com.pnuema.bible.android.ui.read.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.pnuema.bible.android.ui.read.state.VerseViewState

@Composable
fun VerseItem(
    state: VerseViewState
) {
    val annotatedVerse = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary, fontSize = MaterialTheme.typography.bodyMedium.fontSize)) {
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