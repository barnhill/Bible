package com.pnuema.bible.android.ui.read.compose

import android.R.attr.fontStyle
import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.read.state.VerseViewState

@Composable
fun VerseItem(
    state: VerseViewState
) {
    val annotatedVerse = buildAnnotatedString {
        // verse number
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontStyle = MaterialTheme.typography.titleSmall.fontStyle,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                baselineShift = BaselineShift(0.15f)
            )
        ) {
            append("    ")
            append(state.verseNumber.toString())
            append("  ")
        }

        // verse text
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            )
        ) {
            append(state.verseText.trimMargin("¶").replace("¶", "").trim())
        }
    }
    Text(
        text = annotatedVerse,
        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 26.sp),
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun VerseItem_Preview() {
    BibleTheme {
        VerseItem(state = VerseViewState(1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."))
    }
}