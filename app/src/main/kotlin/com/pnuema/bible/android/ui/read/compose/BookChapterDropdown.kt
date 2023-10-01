package com.pnuema.bible.android.ui.read.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.R
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.utils.capitalizeTitleCase

@Composable
fun BookChapterDropdown(
    modifier: Modifier = Modifier,
    book: String,
    chapter: String,
    onClicked: () -> Unit,
) {
    Card(
        modifier = modifier
            .wrapContentWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors()
    ) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
                .clickable { onClicked() },
        ) {
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(end = 8.dp),
                text = "$book $chapter".capitalizeTitleCase(),
                style = BibleTheme.typography.caption2,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Image(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.arrow_down_drop_circle_outline),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
            )
        }
    }
}

@Preview
@Composable
private fun BookChapterDropdown_Preview() {
    BibleTheme {
        BookChapterDropdown(
            book = "GenEsis",
            chapter = "1",
            onClicked = {}
        )
    }
}