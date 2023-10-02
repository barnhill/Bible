package com.pnuema.bible.android.ui.bookchapterverse.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.BibleTheme

@Composable
fun ChapterItem(
    modifier: Modifier = Modifier,
    chapterNumber: Int,
    onClick: () -> Unit,
) {
    val isSelected = chapterNumber == CurrentSelected.chapter
    val backgroundColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable { onClick() }
            .drawBehind {
                if (isSelected) {
                    drawCircle(color = backgroundColor)
                }
            }
    ) {
        Text(
            modifier = Modifier
                .padding(all = 24.dp)
                .align(alignment = Alignment.Center),
            text = chapterNumber.toString(),
            style = BibleTheme.typography.body2,
            color = if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChapterItem_Selected_Preview() {
    BibleTheme {
        ChapterItem(
            chapterNumber = 1,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChapterItem_Unselected_Preview() {
    BibleTheme {
        ChapterItem(
            chapterNumber = 2,
            onClick = {}
        )
    }
}