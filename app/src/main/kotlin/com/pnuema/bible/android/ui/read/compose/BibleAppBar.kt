package com.pnuema.bible.android.ui.read.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.ui.BibleTheme

@Composable
fun BibleAppBar(
    book: String,
    chapter: String,
    versionAbbreviation: String,
    onBookChapterClicked: () -> Unit,
    onVersionClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(56.dp)
            .background(Color.Transparent)
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)).background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 8.dp)
    ) {
        BookChapterDropdown(
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically),
            book = book,
            chapter = chapter,
            onClicked = onBookChapterClicked
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        VersionDropdown(
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically),
            versionAbbreviation = versionAbbreviation,
            onClicked = onVersionClicked
        )
    }
}

@Preview
@Composable
private fun BibleAppBar_Preview() {
    BibleTheme {
        BibleAppBar(
            book = "Genesis",
            chapter = "1",
            versionAbbreviation = "ERV",
            onBookChapterClicked = {},
            onVersionClicked = {}
        )
    }
}