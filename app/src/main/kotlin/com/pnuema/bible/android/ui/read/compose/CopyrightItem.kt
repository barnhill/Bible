package com.pnuema.bible.android.ui.read.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.read.state.CopyrightViewState

@Composable
fun CopyrightItem(
    state: CopyrightViewState
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        text = state.copyright,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.bodySmall
    )
}

@Preview(showBackground = true)
@Composable
private fun CopyrightItem_Preview() {
    BibleTheme {
        CopyrightItem(state = CopyrightViewState("Lorem ipsum dolor sit amet."))
    }
}