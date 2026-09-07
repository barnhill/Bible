package com.pnuema.bible.android.ui.bookchapterverse.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.bookchapterverse.state.BookViewState

@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    book: BookViewState,
    onClick: () -> Unit,
) {
    val isSelected = book.id == CurrentSelected.book

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .clip(RoundedCornerShape(999.dp))
                .background(
                    if (isSelected) MaterialTheme.colorScheme.surfaceContainer
                    else Color.Transparent
                )
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                text = book.name,
                style = if (isSelected) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookItem_Preview() {
    BibleTheme {
        BookItem(
            book = BookViewState(
                id = 1,
                name = "Genesis",
                abbreviation = "Gen"
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookItem_Unselected_Preview() {
    BibleTheme {
        BookItem(
            book = BookViewState(
                id = 2,
                name = "Exodus",
                abbreviation = "Ex"
            ),
            onClick = {}
        )
    }
}