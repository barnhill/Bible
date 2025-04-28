package com.pnuema.bible.android.ui.read.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.ui.BibleTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleAppBar(
    modifier: Modifier = Modifier,
    book: String,
    chapter: String,
    versionAbbreviation: String,
    onBookChapterClicked: () -> Unit,
    onVersionClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
    TopAppBar(
        modifier = modifier
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.primary),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            Row(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val paddingCompensation = 16.dp.toPx().roundToInt()
                        val adjustedConstraints = constraints.copy(
                            // not a good idea inside horizontal scroll view,
                            // but I guess we can assume that's not the case here
                            maxWidth = constraints.maxWidth + paddingCompensation
                        )
                        val placeable = measurable.measure(adjustedConstraints)
                        layout(placeable.width, placeable.height) {
                            placeable.place(-paddingCompensation / 2, 0)
                        }
                    }
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 12.dp)
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
    )
        }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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
