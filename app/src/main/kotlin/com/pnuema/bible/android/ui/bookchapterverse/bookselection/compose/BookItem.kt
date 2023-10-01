package com.pnuema.bible.android.ui.bookchapterverse.bookselection.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pnuema.bible.android.statics.CurrentSelected
import com.pnuema.bible.android.ui.BibleTheme
import com.pnuema.bible.android.ui.bookchapterverse.bookselection.state.BookViewState

@Composable
fun BookItem(
    book: BookViewState,
    onClick: () -> Unit,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .clickable { onClick() },
        text = book.name,
        style = if (book.id == CurrentSelected.book) BibleTheme.typography.body2 else BibleTheme.typography.caption2,
        color = if (book.id == CurrentSelected.book) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
    )
}

@Preview(showBackground = true)
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